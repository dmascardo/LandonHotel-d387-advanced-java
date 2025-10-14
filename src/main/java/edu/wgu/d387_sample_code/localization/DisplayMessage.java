package edu.wgu.d387_sample_code.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayMessage implements Runnable {
    private final Locale locale;
    private String message;

    public DisplayMessage(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void run() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            message = bundle.getString("welcome");
        } catch (Exception e) {
            message = "Error loading message for locale " + locale;
        }
    }

    public String getMessage() {
        return "[" + locale.getDisplayLanguage() + "] " + message;
    }
}
