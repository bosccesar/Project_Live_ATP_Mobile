package com.atp.live_atp_mobile;

/**
 * Created by cebosc on 07/03/2018.
 */

public enum Tournament {
    OPEN_AUSTRALIA("Open Australia", 0),
    ROLAND_GARROS("Rolland Garros", 1),
    WIMBELDON("Wimbledon", 2),
    US_OPEN("US Open", 3);

    private String stringValue;
    private int intValue;

    Tournament(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
