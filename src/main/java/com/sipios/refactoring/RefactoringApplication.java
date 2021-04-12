package com.sipios.refactoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.TimeZone;

@SpringBootApplication
public class RefactoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefactoringApplication.class, args);
    }

    @Bean
    public Calendar calendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
    }
}
