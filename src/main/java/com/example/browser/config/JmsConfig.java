package com.example.browser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class JmsConfig {
    public static final String QUEUE_NAME = "browser-demo-queue";
}
