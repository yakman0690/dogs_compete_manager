/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.controller;

import java.net.URL;
import org.apache.activemq.broker.BrokerService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Test
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PublicControllerTest {

    @LocalServerPort
    private int port;
    
    @MockBean
    private BrokerService brokerService;

    @Autowired
    private TestRestTemplate restTemplate;    


    @Test
    public void test() throws Exception {
       

        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/public/test").toString(), String.class);
       
        assertEquals("test", response.getBody());

    }
}
