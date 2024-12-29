package com.example.selector.service;


import com.example.selector.model.NewsMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private final JmsTemplate jmsTemplate;

    public NewsService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendNewsMessage(String type, String content, String priority) {
        NewsMessage message = new NewsMessage(type, content, priority);
        jmsTemplate.convertAndSend("newsQueue", message, msg -> {
            msg.setStringProperty("NewsType", type);
            msg.setStringProperty("Priority", priority);
            return msg;
        });
        System.out.println("Sent: " + message);
    }
}

