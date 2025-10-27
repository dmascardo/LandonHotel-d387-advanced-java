//package edu.wgu.d387_sample_code.controller;
//
//import edu.wgu.d387_sample_code.localization.DisplayMessage;
//import edu.wgu.d387_sample_code.time.TimeZoneConversion;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.Locale;
//
//@RestController
//public class DisplayMessageController {
//
//    @GetMapping("/api/info")
//    public String getDisplayInfo() throws InterruptedException {
//
//        DisplayMessage englishTask = new DisplayMessage(Locale.ENGLISH);
//        DisplayMessage frenchTask = new DisplayMessage(Locale.FRENCH);
//
//
//        Thread enThread = new Thread(englishTask);
//        Thread frThread = new Thread(frenchTask);
//
//        enThread.start();
//        frThread.start();
//
//        enThread.join();
//        frThread.join();
//
//
//
//        String englishMessage = englishTask.getMessage();
//        String frenchMessage = frenchTask.getMessage();
//
//
//
//
//        ZonedDateTime nowET = ZonedDateTime.now(ZoneId.of("America/Toronto"));
//        String timeOutput = TimeZoneConversion.convertTimeZones(nowET);
//
//
//        return timeOutput + "<br>" + englishMessage + "<br>" + frenchMessage;
//    }
//}