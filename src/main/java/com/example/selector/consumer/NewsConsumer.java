package com.example.selector.consumer;

import com.example.selector.model.NewsMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class NewsConsumer {

    @JmsListener(
            destination = "newsQueue",
            selector = "NewsType = 'Sports' OR NewsType = 'Opinion'"
    )
    public void handleSportsAndOpinionNews(NewsMessage message) {
        System.out.println("Received Sports/Opinion news: " + message);
    }

    @JmsListener(
            destination = "newsQueue",
            selector = "Priority = 'High'"
    )
    public void handleHighPriorityNews(NewsMessage message) {
        System.out.println("Received High Priority news: " + message);
    }

    @JmsListener(
            destination = "newsQueue",
            selector = "NewsType = 'Politics'"
    )
    public void handlePoliticsNews(NewsMessage message) {
        System.out.println("Received Politics news: " + message);
    }
}