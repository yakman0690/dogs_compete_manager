/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman;

import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author Test
 */
@SpringBootTest
public class ContextLoadTest {

    @Test
    void contextLoads() {

    }
    //no need of real activemq service for tests
    @MockBean
    private BrokerService brokerService;

}
