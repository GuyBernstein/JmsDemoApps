package com.example.durable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MessagePublisher {
    private final JmsTemplate jmsTemplate;
    private static final String TOPIC_NAME = "durableTopic";
    private final AtomicInteger messageCounter = new AtomicInteger(0);

    @Autowired
    public MessagePublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publishMessage(String message) {
        int count = messageCounter.incrementAndGet();
        String fullMessage = String.format("Message #%d: %s", count, message);

        jmsTemplate.convertAndSend(TOPIC_NAME, fullMessage);
        System.out.println("Published: " + fullMessage);
    }
}