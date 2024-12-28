package com.example.p2p.sender;


import com.example.p2p.config.QueueConfig;
import com.example.p2p.model.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {
    private final JmsTemplate jmsTemplate;

    public OrderSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrder(String content, int orderNumber) {
        Message message = new Message(content, orderNumber);
        jmsTemplate.convertAndSend(QueueConfig.ORDER_QUEUE, message);
        System.out.println("Order sent to queue: " + message);
    }
}
