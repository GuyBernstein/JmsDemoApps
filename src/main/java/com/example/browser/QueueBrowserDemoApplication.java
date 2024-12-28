package com.example.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class QueueBrowserDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueueBrowserDemoApplication.class, args);
    }
}
