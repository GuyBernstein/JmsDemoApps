package com.example.pubsub.service;

import com.example.pubsub.model.NewsMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class NewsSubscriber2 {
    @JmsListener(destination = "news.general")
    public void receiveGeneralNews(NewsMessage news) {
        System.out.println("Subscriber 2 received general news: " + news);
    }
}