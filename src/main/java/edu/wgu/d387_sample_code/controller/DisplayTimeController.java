package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.time.DisplayTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DisplayTimeController {

    @GetMapping("/api/presentation-times")
    public Map<String, String> getTimes() throws InterruptedException {
        Map<String, String> times = Collections.synchronizedMap(new HashMap<>());

        DisplayTime et = new DisplayTime("ET", ZoneId.of("America/New_York"));
        DisplayTime mt = new DisplayTime("MT", ZoneId.of("America/Denver"));
        DisplayTime utc = new DisplayTime("UTC", ZoneId.of("UTC"));

        Thread etThread = new Thread(et);
        Thread mtThread = new Thread(mt);
        Thread utcThread = new Thread(utc);

        etThread.start();
        mtThread.start();
        utcThread.start();

        etThread.join();
        mtThread.join();
        utcThread.join();

        times.put(et.getZoneLabel(), et.getTime());
        times.put(mt.getZoneLabel(), mt.getTime());
        times.put(utc.getZoneLabel(), utc.getTime());

        return times;
    }
}