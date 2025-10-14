package edu.wgu.d387_sample_code.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZoneConversion {

    public static String convertTimeZones(ZonedDateTime etTime) {
        // 24-hour "HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Convert to Mountain Time and UTC
        ZonedDateTime mtTime = etTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime utcTime = etTime.withZoneSameInstant(ZoneId.of("UTC"));

        // Format times for display
        String etFormatted = etTime.format(formatter);
        String mtFormatted = mtTime.format(formatter);
        String utcFormatted = utcTime.format(formatter);

        return "Live Presentation Times:\n" +
                "ET: " + etFormatted + "\n" +
                "MT: " + mtFormatted + "\n" +
                "UTC: " + utcFormatted;
    }
}