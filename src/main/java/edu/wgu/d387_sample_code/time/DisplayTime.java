package edu.wgu.d387_sample_code.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DisplayTime implements Runnable {

    private final String zoneLabel;
    private final ZoneId zoneId;
    private String time;

    public DisplayTime(String zoneLabel, ZoneId zoneId) {
        this.zoneLabel = zoneLabel;
        this.zoneId = zoneId;
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        this.time = now.format(formatter);
    }

    public String getZoneLabel() {
        return zoneLabel;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public String getTime() {
        return time;
    }
}