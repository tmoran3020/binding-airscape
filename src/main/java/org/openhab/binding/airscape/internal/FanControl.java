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

/**
 * The {@link FanControl} enum encapsulates the integer command to control the fan
 *
 * @author Tim Moran - Initial contribution
 */

public enum FanControl {
    FAN_SPEED_UP(1),
    FAN_SPEED_DOWN(3),
    FAN_OFF(4),
    ADD_HOUR(2);

    private int controlCode;

    private FanControl(int controlCode) {
        this.controlCode = controlCode;
    }

    public int getControlCode() {
        return controlCode;
    }
}
