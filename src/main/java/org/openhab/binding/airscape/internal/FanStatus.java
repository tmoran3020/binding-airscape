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
 * The {@link FanStatus} class contains returned fan status values
 *
 * @author Tim Moran - Initial contribution
 */

@NonNullByDefault
public class FanStatus {
    private int fanspd;
    private int doorinprocess;
    private int timeremaining;
    private String macaddr = "";
    private String ipaddr = "";
    private String model = "";
    private String softver = "";
    private int interlock1;
    private int interlock2;
    private int cfm;
    private int power;
    private int inside;
    private int attic;
    private int oa;
    private String dip_switches = "";
    private String remote_switch = "";
    private int setpoint;
    private String dns = "";

    public int getFanspd() {
        return fanspd;
    }

    public void setFanspd(int fanspd) {
        this.fanspd = fanspd;
    }

    public int getDoorinprocess() {
        return doorinprocess;
    }

    public void setDoorinprocess(int doorinprocess) {
        this.doorinprocess = doorinprocess;
    }

    public int getTimeremaining() {
        return timeremaining;
    }

    public void setTimeremaining(int timeremaining) {
        this.timeremaining = timeremaining;
    }

    public String getMacaddr() {
        return macaddr;
    }

    public void setMacaddr(String macaddr) {
        this.macaddr = macaddr;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSoftver() {
        return softver;
    }

    public void setSoftver(String softver) {
        this.softver = softver;
    }

    public int getInterlock1() {
        return interlock1;
    }

    public void setInterlock1(int interlock1) {
        this.interlock1 = interlock1;
    }

    public int getInterlock2() {
        return interlock2;
    }

    public void setInterlock2(int interlock2) {
        this.interlock2 = interlock2;
    }

    public int getCfm() {
        return cfm;
    }

    public void setCfm(int cfm) {
        this.cfm = cfm;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getInside() {
        return inside;
    }

    public void setInside(int inside) {
        this.inside = inside;
    }

    public int getAttic() {
        return attic;
    }

    public void setAttic(int attic) {
        this.attic = attic;
    }

    public int getOa() {
        return oa;
    }

    public void setOa(int oa) {
        this.oa = oa;
    }

    public String getDip_switches() {
        return dip_switches;
    }

    public void setDip_switches(String dip_switches) {
        this.dip_switches = dip_switches;
    }

    public String getRemote_switch() {
        return remote_switch;
    }

    public void setRemote_switch(String remote_switch) {
        this.remote_switch = remote_switch;
    }

    public int getSetpoint() {
        return setpoint;
    }

    public void setSetpoint(int setpoint) {
        this.setpoint = setpoint;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    @Override
    public String toString() {
        return "FanStatus [fanspd=" + fanspd + ", doorinprocess=" + doorinprocess + ", timeremaining=" + timeremaining
                + ", macaddr=" + macaddr + ", ipaddr=" + ipaddr + ", model=" + model + ", softver=" + softver
                + ", interlock1=" + interlock1 + ", interlock2=" + interlock2 + ", cfm=" + cfm + ", power=" + power
                + ", inside=" + inside + ", attic=" + attic + ", oa=" + oa + ", dip_switches=" + dip_switches
                + ", remote_switch=" + remote_switch + ", setpoint=" + setpoint + ", dns=" + dns + "]";
    }

}
