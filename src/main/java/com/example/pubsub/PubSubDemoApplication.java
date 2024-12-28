package com.example.pubsub;

import com.example.pubsub.config.PubSubConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(PubSubConfig.class)
public class PubSubDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PubSubDemoApplication.class, args);
    }
}
