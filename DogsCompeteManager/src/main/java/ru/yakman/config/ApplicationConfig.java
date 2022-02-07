/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.config;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
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
//TODO: add csrf
@Configuration
@ComponentScan("ru.yakman")
public class ApplicationConfig {

    @Value("${spring.activemq.brokerUrl}")
    private String brokerUrl;

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

    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        return connectionFactory;
    }
    
    //for topic tests only
    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService broker() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.setUseJmx(false);
        broker.addConnector(brokerUrl);
        broker.setDataDirectory("/mqdata/");
        broker.setPersistent(true);
        return broker;
    }

}
