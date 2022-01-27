package ru.yakman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
public class DogsCompeteManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogsCompeteManagerApplication.class, args);
    }

   
}
