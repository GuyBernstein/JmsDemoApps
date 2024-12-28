package com.example.pubsub.config;

import com.example.pubsub.model.NewsMessage;
import com.example.pubsub.service.NewsPublisher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.pubsub.model.NewsMessage.NewsMessageBuilder.aNewsMessage;

@Configuration
public class PubSubConfig {
    @Bean
    ApplicationRunner demoPubSub(NewsPublisher publisher) {
        return args -> {
            System.out.println("\n=== Demonstrating Publish-Subscribe Messaging ===");
            System.out.println("Broadcasting news updates to subscribers...\n");

            // Publish general news (both subscribers will receive)
            for (int i = 1; i <= 2; i++) {
                NewsMessage generalNews =  aNewsMessage()
                        .category("General")
                        .content("Breaking News #" + i)
                        .messageNumber(i)
                        .build();

                publisher.publishNews("news.general", generalNews);
                Thread.sleep(1000);
            }

            // Publish sports news (only subscriber 1 will receive)
            for (int i = 1; i <= 2; i++) {
                NewsMessage sportsNews =  aNewsMessage()
                        .category("Sports")
                        .content("Sports Update #" + i)
                        .messageNumber(i)
                        .build();

                publisher.publishNews("news.sports", sportsNews);
                Thread.sleep(1000);
            }
        };
    }
}
