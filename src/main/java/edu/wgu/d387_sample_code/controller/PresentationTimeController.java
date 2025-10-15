package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.time.HotelPresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PresentationTimeController {

    @GetMapping("/api/hotel-presentation-times")
    public Map<String, String> getPresentationTimes() {
        HotelPresentation task = new HotelPresentation();
        Thread thread = new Thread(task);
        thread.start();

        try {
            thread.join(); // Wait for task to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return task.getFormattedTimes();
    }
}