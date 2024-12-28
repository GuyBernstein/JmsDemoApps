package com.example.shared.service;

import org.apache.activemq.artemis.jms.client.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SharedTopicPublisher {
    private final JmsTemplate jmsTemplate;
    private final ActiveMQTopic topic;
    private final AtomicInteger messageCounter = new AtomicInteger(0);

    @Autowired
    public SharedTopicPublisher(JmsTemplate jmsTemplate, ActiveMQTopic topic) {
        this.jmsTemplate = jmsTemplate;
        this.topic = topic;
    }

    public void publishMessage(String message) {
        int count = messageCounter.incrementAndGet();
        String fullMessage = String.format("Shared Message #%d: %s", count, message);

        jmsTemplate.convertAndSend(topic, fullMessage);
        System.out.println("Published to shared topic: " + fullMessage);
    }
}