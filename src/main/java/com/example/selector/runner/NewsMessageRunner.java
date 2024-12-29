package com.example.selector.runner;


import com.example.selector.service.NewsService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NewsMessageRunner {

    private final NewsService newsService;

    public NewsMessageRunner(NewsService newsService) {
        this.newsService = newsService;
    }

    @Bean
    ApplicationRunner sendMessages() {
        return args -> {
            // Send different types of news messages
            newsService.sendNewsMessage("Sports", "Barcelona wins Champions League", "Normal");
            newsService.sendNewsMessage("Politics", "New legislation passed", "High");
            newsService.sendNewsMessage("Opinion", "Editorial on climate change", "Normal");
            newsService.sendNewsMessage("Weather", "Storm warning", "High");
            newsService.sendNewsMessage("Sports", "Olympic medals update", "High");
        };
    }
}
