package com.example.browser.service;

import com.example.browser.config.JmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;
import java.util.Objects;

@Service
public class MessageSenderService {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public MessageSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessages() {
        System.out.println("Sending messages to the queue...");

        // Send messages with a custom property for filtering
        jmsTemplate.send(JmsConfig.QUEUE_NAME, session -> {
            TextMessage message = session.createTextMessage("Hello from JMS!");
            message.setStringProperty("messageContent", message.getText());
            message.setIntProperty("messageIndex", 1);
            return message;
        });

        jmsTemplate.send(JmsConfig.QUEUE_NAME, session -> {
            TextMessage message = session.createTextMessage("Queue browsing demo");
            message.setStringProperty("messageContent", message.getText());
            message.setIntProperty("messageIndex", 2);
            return message;
        });

        jmsTemplate.send(JmsConfig.QUEUE_NAME, session -> {
            TextMessage message = session.createTextMessage("Last message");
            message.setStringProperty("messageContent", message.getText());
            message.setIntProperty("messageIndex", 3);
            return message;
        });

        System.out.println("Three messages sent to the queue.");
    }

    public void consumeSpecificMessage(int messageIndex) {
        System.out.println("\nAttempting to consume message with index: " + messageIndex);
        try {
            String selector = "messageIndex = " + messageIndex;
            String message = Objects.requireNonNull
                    (jmsTemplate.receiveSelectedAndConvert(JmsConfig.QUEUE_NAME, selector))
                    .toString();
            System.out.println("Consumed message: " + message);
        } catch (Exception e) {
            System.err.println("Error consuming message: " + e.getMessage());
        }
    }
}
