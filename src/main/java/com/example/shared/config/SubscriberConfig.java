package com.example.shared.config;

import com.example.shared.service.SharedMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriberConfig {

    @Bean
    public SharedMessageSubscriber subscriber1() {
        return new SharedMessageSubscriber();
    }

    @Bean
    public SharedMessageSubscriber subscriber2() {
        return new SharedMessageSubscriber();
    }

    @Bean
    public SharedMessageSubscriber subscriber3() {
        return new SharedMessageSubscriber();
    }
}
