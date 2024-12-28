package com.example.pubsub.service;

import com.example.pubsub.model.NewsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NewsPublisher {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishNews(String topic, NewsMessage news) {
        jmsTemplate.convertAndSend(topic, news);
        System.out.println("Published to " + topic + ": " + news);
    }
}
