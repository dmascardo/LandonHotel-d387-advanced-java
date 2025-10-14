package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.localization.DisplayMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class D387SampleCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(D387SampleCodeApplication.class, args);

        //  Multithreaded welcome messages
        Thread englishThread = new Thread(new DisplayMessage(Locale.ENGLISH));  //
        Thread frenchThread = new Thread(new DisplayMessage(Locale.FRENCH));    //

        englishThread.start();  //
        frenchThread.start();   //
    }
}