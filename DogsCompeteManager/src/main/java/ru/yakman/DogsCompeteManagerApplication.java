package ru.yakman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DogsCompeteManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogsCompeteManagerApplication.class, args);
    }

}
