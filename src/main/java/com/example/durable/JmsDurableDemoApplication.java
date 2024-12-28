package com.example.durable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsDurableDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JmsDurableDemoApplication.class, args);
    }
}
