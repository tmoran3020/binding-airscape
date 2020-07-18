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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link AirscapeBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Tim Moran - Initial contribution
 */
@NonNullByDefault
public class AirscapeBindingConstants {

    private static final String BINDING_ID = "airscape";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_FAN = new ThingTypeUID(BINDING_ID, "fan");

    // List of all Channel ids
    public static final String CHANNEL_FAN_CONTROL = "fanControl";
    public static final String CHANNEL_FAN_SPEED = "speed";
    public static final String CHANNEL_FAN_TIME_REMAINING = "timeRemaining";
    public static final String CHANNEL_FAN_ADD_ONE_HOUR = "addOneHour";
    public static final String CHANNEL_FAN_DOOR_IN_PROCESS = "doorInProcess";
    public static final String CHANNEL_FAN_MODEL = "model";
    public static final String CHANNEL_FAN_SOFT_VER = "softwareVersion";
    public static final String CHANNEL_FAN_CFM = "cfm";
    public static final String CHANNEL_FAN_POWER = "power";
    public static final String CHANNEL_FAN_INSIDE_TEMP = "insideTemperature";
    public static final String CHANNEL_FAN_ATTIC_TEMP = "atticTemperature";
    public static final String CHANNEL_FAN_OUTSIDE_TEMP = "outsideTemperature";
    public static final String CHANNEL_FAN_INTERLOCK_1 = "interlock1";
    public static final String CHANNEL_FAN_INTERLOCK_2 = "interlock2";
    public static final String CHANNEL_FAN_MAC_ADDRESS = "macAddress";

    public static final int DEFAULT_POLLING_INTERVAL_IN_SECONDS = 30;
}
