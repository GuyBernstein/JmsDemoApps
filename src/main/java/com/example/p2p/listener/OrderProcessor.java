package com.example.p2p.listener;


import com.example.p2p.config.QueueConfig;
import com.example.p2p.model.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor {
    @JmsListener(destination = QueueConfig.ORDER_QUEUE)
    public void processOrder(Message message) {
        System.out.println("Processing order: " + message);
        // Simulate order processing
        try {
            Thread.sleep(500);
            System.out.println("Order " + message.getMessageNumber() + " processed successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
