package edu.wgu.d387_sample_code.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class HotelPresentation implements Runnable {

    private Map<String, String> formattedTimes;

    public Map<String, String> getFormattedTimes() {
        return formattedTimes;
    }

    @Override
    public void run() {
        formattedTimes = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        formattedTimes.put("ET", ZonedDateTime.now(ZoneId.of("America/New_York")).format(formatter));
        formattedTimes.put("MT", ZonedDateTime.now(ZoneId.of("America/Denver")).format(formatter));
        formattedTimes.put("UTC", ZonedDateTime.now(ZoneId.of("UTC")).format(formatter));
    }
}
