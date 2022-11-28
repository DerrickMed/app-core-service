package ru.sbrf.sell.appcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AppcoreApplication {

    public static void main(String[] args) {SpringApplication.run(AppcoreApplication.class, args);}


}
