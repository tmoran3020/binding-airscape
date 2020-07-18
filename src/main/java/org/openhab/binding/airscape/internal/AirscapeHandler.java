/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.airscape.internal;

import static org.openhab.binding.airscape.internal.AirscapeBindingConstants.*;

import java.io.IOException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.HttpMethod;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.eclipse.smarthome.core.library.types.StopMoveType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.library.types.UpDownType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.eclipse.smarthome.io.net.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * The {@link AirscapeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Tim Moran - Initial contribution
 */
@NonNullByDefault
public class AirscapeHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(AirscapeHandler.class);

    private @Nullable ScheduledFuture<?> pollingJob;

    public AirscapeHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("Received command [{}] channel [{}]", command, channelUID);

        if (command.equals(RefreshType.REFRESH)) {
            // probably need a cache, this is getting overloaded
            getFanStatus();
        }

        if (CHANNEL_FAN_CONTROL.equals(channelUID.getId())) {
            if (command.equals(UpDownType.UP)) {
                sendCommand(FanControl.FAN_SPEED_UP);
            } else if (command.equals(UpDownType.DOWN)) {
                sendCommand(FanControl.FAN_SPEED_DOWN);
            } else if (command.equals(StopMoveType.STOP)) {
                sendCommand(FanControl.FAN_OFF);
            }
        }

        if (CHANNEL_FAN_ADD_ONE_HOUR.equals(channelUID.getId())) {
            if (command.equals(OnOffType.ON)) {
                sendCommand(FanControl.ADD_HOUR);
            }
        }
    }

    @Override
    public void initialize() {
        logger.debug("Start initializing!");
        AirscapeConfiguration localConfig = getConfigAs(AirscapeConfiguration.class);

        String ipAddress = localConfig.getIpAddress();

        if (StringUtils.isEmpty(ipAddress)) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "No IP address");
        }

        updateStatus(ThingStatus.UNKNOWN);

        scheduler.execute(() -> {
            FanStatus response = getFanStatus();

            if (response != null) {
                updateStatus(ThingStatus.ONLINE);

                int pollingInterval = localConfig.getPollingInterval();
                logger.debug("Start polling job at interval {}s", pollingInterval);
                pollingJob = scheduler.scheduleWithFixedDelay(this::getFanStatus, pollingInterval, pollingInterval,
                        TimeUnit.SECONDS);
            }
        });
        logger.debug("Finished initializing!");
    }

    private @Nullable FanStatus getFanStatus() {
        AirscapeConfiguration config = getConfigAs(AirscapeConfiguration.class);
        String url = "http://" + config.getIpAddress() + "/status.json.cgi";
        String response = "";
        try {
            response = HttpUtil.executeUrl(HttpMethod.GET, url, 2000);
            response = preprocess(response);

            logger.debug("Polling airscape fan {}", config.getIpAddress());
            Gson gson = new Gson();
            FanStatus status = gson.fromJson(response, FanStatus.class);

            if (getThing().getStatus() != ThingStatus.ONLINE) {
                updateStatus(ThingStatus.ONLINE);
            }

            updateChannels(status);

            return status;
        } catch (IOException ioe) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, ioe.getMessage());
            return null;
        }
    }

    private void updateChannels(FanStatus status) {
        updateState(CHANNEL_FAN_SPEED, new DecimalType(status.getFanspd()));
        updateState(CHANNEL_FAN_TIME_REMAINING, new DecimalType(status.getTimeremaining()));
        updateState(CHANNEL_FAN_ATTIC_TEMP, new DecimalType(status.getAttic()));
        updateState(CHANNEL_FAN_CFM, new DecimalType(status.getCfm()));
        updateState(CHANNEL_FAN_DOOR_IN_PROCESS, convertToOpenClosedType(status.getDoorinprocess()));
        updateState(CHANNEL_FAN_INSIDE_TEMP, new DecimalType(status.getInside()));
        updateState(CHANNEL_FAN_INTERLOCK_1, convertToOpenClosedType(status.getInterlock1()));
        updateState(CHANNEL_FAN_INTERLOCK_2, convertToOpenClosedType(status.getInterlock2()));
        updateState(CHANNEL_FAN_MAC_ADDRESS, new StringType(status.getMacaddr()));
        updateState(CHANNEL_FAN_MODEL, new StringType(status.getModel()));
        updateState(CHANNEL_FAN_OUTSIDE_TEMP, new DecimalType(status.getOa()));
        updateState(CHANNEL_FAN_POWER, new DecimalType(status.getPower()));
        updateState(CHANNEL_FAN_SOFT_VER, new StringType(status.getSoftver()));
    }

    private OpenClosedType convertToOpenClosedType(int input) {
        if (input == 1) {
            return OpenClosedType.OPEN;
        }
        return OpenClosedType.CLOSED;
    }

    /*
     * Removing the "server_response" field pre-parsing as it is not parseable - control characters, unescaped
     * quotes. To do this, we assume ordering of the JSON which is not awesome. But not escaping your JSON is worse.
     */
    private String preprocess(String input) {
        int serverResponseBeginning = input.indexOf("\"server_response", 0);
        int dipSwitchBeginning = input.indexOf("\"dip_switches", serverResponseBeginning);

        return input.substring(0, serverResponseBeginning) + input.substring(dipSwitchBeginning);
    }

    private @Nullable FanStatus sendCommand(FanControl control) {
        AirscapeConfiguration config = getConfigAs(AirscapeConfiguration.class);
        String url = "http://" + config.getIpAddress() + "/fanspd.cgi?dir=" + control.getControlCode();
        try {
            logger.debug("Sending control code [{}] to url [{}]", control.getControlCode(), url);

            HttpUtil.executeUrl(HttpMethod.GET, url, 2000);

            // The returned information is completely non-standard output. Don't both, just go get current status.

            return getFanStatus();
        } catch (IOException ioe) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, ioe.getMessage());
            return null;
        }
    }

    /**
     * Stops the polling.
     */
    private void stopPolling() {
        ScheduledFuture<?> localPollingJob = pollingJob;
        if (localPollingJob != null && !localPollingJob.isCancelled()) {
            logger.debug("Stop polling job");
            localPollingJob.cancel(true);
            pollingJob = null;
        }
    }

    @Override
    public void dispose() {
        stopPolling();
    }

}
