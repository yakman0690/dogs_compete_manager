/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.jms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yakman.service.EventGetService;

/**
 *
 * @author Test
 */
@Component
public class JMSMessageReceiver {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private EventGetService service;

    @Value("${jms.queue.name}")
    private String queueName;
    @Value("${jms.topic.name}")
    private String topicName;

    private Connection connection;

    Logger logger = LoggerFactory.getLogger(JMSMessageReceiver.class);

    @PostConstruct
    public void init() {
        try {
            connection = connectionFactory.createConnection();

            Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            //Queue queue = session.createQueue(queueName);
            Topic topic = session.createTopic(topicName);
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(message -> {
                service.updateEventList();
            });
            connection.start();
        } catch (JMSException ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    @PreDestroy
    public void destroy() {
        try {
            connection.stop();
            connection.close();
        } catch (JMSException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
