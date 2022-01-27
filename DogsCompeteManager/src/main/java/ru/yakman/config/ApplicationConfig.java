/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import ru.yakman.service.IUserService;
import ru.yakman.service.impl.UserServiceImpl;

/**
 *
 * @author Yakman Beans configuration class
 */
@Configuration
@ComponentScan("ru.yakman")
public class ApplicationConfig {

    @Bean
    public IUserService userService() {
        return new UserServiceImpl();
    }

    @Bean(name = "jacksonHttpMessageConverter")
    public MappingJackson2HttpMessageConverter getJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setPrettyPrint(true);
        return converter;
    }

}
