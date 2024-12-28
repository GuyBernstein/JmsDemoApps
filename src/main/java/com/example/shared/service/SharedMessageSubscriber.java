package com.example.shared.service;

import org.springframework.jms.annotation.JmsListener;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedMessageSubscriber {
    private static final String TOPIC_NAME = "Consumer.shared.VirtualTopic.sharedTopic";
    private final String subscriberId;
    private final AtomicInteger messageCount = new AtomicInteger(0);

    public SharedMessageSubscriber() {
        this.subscriberId = "subscriber-" + UUID.randomUUID().toString().substring(0, 8);
    }

    @JmsListener(
            destination = TOPIC_NAME,
            containerFactory = "jmsListenerContainerFactory"
    )
    public void receiveMessage(String message) {
        int count = messageCount.incrementAndGet();
        System.out.println(subscriberId + " received: " + message);
    }
    public String getSubscriberId() {
        return subscriberId;
    }

    public int getMessageCount() {
        return messageCount.get();
    }
}