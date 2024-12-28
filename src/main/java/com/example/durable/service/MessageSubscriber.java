package com.example.durable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class MessageSubscriber {
    private static final String TOPIC_NAME = "durableTopic";
    private final JmsListenerEndpointRegistry registry;
    private final String listenerId = "durableDemo";

    @Autowired
    public MessageSubscriber(JmsListenerEndpointRegistry registry) {
        this.registry = registry;
    }


    @JmsListener(
            id = "durableDemo",
            destination = TOPIC_NAME,
            containerFactory = "durableTopicListenerFactory",
            subscription = "durableDemo"
    )
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }

    public void unsubscribe() {
        MessageListenerContainer container = registry.getListenerContainer(listenerId);
        if (container != null) {
            container.stop();
            System.out.println("Subscriber disconnected!");
        }
    }

    public void resubscribe() {
        MessageListenerContainer container = registry.getListenerContainer(listenerId);
        if (container != null && !container.isRunning()) {
            container.start();
            System.out.println("Subscriber reconnected!");
        }
    }
}
