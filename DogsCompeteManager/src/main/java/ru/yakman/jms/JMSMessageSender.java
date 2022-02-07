/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Test
 */
@Service
public class JMSMessageSender {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${jms.queue.name}")
    private String queueName;
    @Value("${jms.topic.name}")
    private String topicName;

    private Logger logger = LoggerFactory.getLogger(JMSMessageSender.class);

    public void send() {
        try {
            Connection connection = connectionFactory.createConnection();

            Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            //Queue queue = session.createQueue(queueName);
            Topic topic = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(topic);

            producer.send(session.createTextMessage());
        } catch (JMSException ex) {
        }
    }

}
