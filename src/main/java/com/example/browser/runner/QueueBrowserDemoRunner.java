package com.example.browser.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.example.browser.service.MessageSenderService;
import com.example.browser.service.QueueBrowserService;

@Component
public class QueueBrowserDemoRunner {
    private final MessageSenderService messageSenderService;
    private final QueueBrowserService queueBrowserService;

    @Autowired
    public QueueBrowserDemoRunner(
            MessageSenderService messageSenderService,
            QueueBrowserService queueBrowserService) {
        this.messageSenderService = messageSenderService;
        this.queueBrowserService = queueBrowserService;
    }

    @Bean
    public ApplicationRunner demonstrateQueueBrowser() {
        return args -> {
            // Send initial messages
            messageSenderService.sendMessages();

            // Browse all messages
            queueBrowserService.browseMessages();

            // Consume middle message (index 2)
            messageSenderService.consumeSpecificMessage(2);

            // Browse again to show the difference
            queueBrowserService.browseMessages();

            // Demonstrate selector (now using messageContent property)
            queueBrowserService.browseMessagesWithSelector("messageContent LIKE '%Hello%'");
        };
    }
}
