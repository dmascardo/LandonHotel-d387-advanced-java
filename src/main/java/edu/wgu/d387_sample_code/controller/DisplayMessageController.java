package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.localization.DisplayMessage;
import edu.wgu.d387_sample_code.time.TimeZoneConversion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

@RestController
public class DisplayMessageController {

    @GetMapping("/api/info")
    public String getDisplayInfo() throws InterruptedException {
        // Create welcome message tasks
        DisplayMessage englishTask = new DisplayMessage(Locale.ENGLISH);
        DisplayMessage frenchTask = new DisplayMessage(Locale.FRENCH);

        // Run in parallel threads
        Thread enThread = new Thread(englishTask);
        Thread frThread = new Thread(frenchTask);

        enThread.start();
        frThread.start();

        enThread.join();
        frThread.join();


        // Get formatted messages
        String englishMessage = englishTask.getMessage();
        String frenchMessage = frenchTask.getMessage();



        // Get current ET time and convert to MT and UTC
        ZonedDateTime nowET = ZonedDateTime.now(ZoneId.of("America/Toronto"));
        String timeOutput = TimeZoneConversion.convertTimeZones(nowET);

        // Return formatted HTML response
        return timeOutput + "<br>" + englishMessage + "<br>" + frenchMessage;
    }
}