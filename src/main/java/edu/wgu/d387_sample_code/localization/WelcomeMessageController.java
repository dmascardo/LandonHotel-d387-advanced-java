package edu.wgu.d387_sample_code.localization;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/api/welcome")
public class WelcomeMessageController {

    @GetMapping
    public Map<String, String> getWelcomeMessages() throws InterruptedException {
        Map<String, String> messages = Collections.synchronizedMap(new HashMap<>());

        Thread englishThread = new Thread(() -> {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
            messages.put("english", bundle.getString("welcome"));
        });

        Thread frenchThread = new Thread(() -> {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.FRENCH);
            messages.put("french", bundle.getString("welcome"));
        });

        englishThread.start();
        frenchThread.start();

        englishThread.join();
        frenchThread.join();

        return messages;
    }
}