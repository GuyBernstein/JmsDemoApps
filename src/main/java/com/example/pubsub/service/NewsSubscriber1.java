package com.example.pubsub.service;

import com.example.pubsub.model.NewsMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class NewsSubscriber1 {
    @JmsListener(destination = "news.general")
    public void receiveGeneralNews(NewsMessage news) {
        System.out.println("Subscriber 1 received general news: " + news);
    }

    @JmsListener(destination = "news.sports")
    public void receiveSportsNews(NewsMessage news) {
        System.out.println("Subscriber 1 received sports news: " + news);
    }
}
