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

/**
 * The {@link AirscapeConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Tim Moran - Initial contribution
 */
@NonNullByDefault
public class AirscapeConfiguration {

    /**
     * Configuration parameters
     */
    private String ipAddress = "";
    private int pollingInterval = AirscapeBindingConstants.DEFAULT_POLLING_INTERVAL_IN_SECONDS;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

}
