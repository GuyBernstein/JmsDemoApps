package com.example.durable.runner;

import com.example.durable.service.MessagePublisher;
import com.example.durable.service.MessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class JmsDemoRunner implements ApplicationRunner {
    private final Logger log = LoggerFactory.getLogger(JmsDemoRunner.class);
    private final MessagePublisher publisher;
    private final MessageSubscriber subscriber;
    private final ScheduledExecutorService executor;

    @Autowired
    public JmsDemoRunner(MessagePublisher publisher, MessageSubscriber subscriber) {
        this.publisher = publisher;
        this.subscriber = subscriber;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        demonstrateDurableSubscription();
    }

    private void demonstrateDurableSubscription() throws InterruptedException {
        log.info("Starting durable subscription demonstration...");

        // Phase 1: Send initial messages with subscriber connected
        log.info("Phase 1: Sending messages with subscriber connected");
        sendMessages(3);
        Thread.sleep(2000);

        // Phase 2: Unsubscribe and send messages
        log.info("Phase 2: Unsubscribing subscriber and sending messages");
        subscriber.unsubscribe();
        Thread.sleep(1000);
        sendMessages(3);

        // Phase 3: Resubscribe and verify missed messages are received
        log.info("Phase 3: Resubscribing subscriber - should receive missed messages");
        subscriber.resubscribe();
        Thread.sleep(1000);

        // Phase 4: Send final messages
        log.info("Phase 4: Sending final messages with subscriber reconnected");
        sendMessages(2);

        executor.shutdown();
    }

    private void sendMessages(int count) throws InterruptedException {
        for (int i = 1; i <= count; i++) {
            publisher.publishMessage("Durable message");
            Thread.sleep(1000);
        }
    }

    @PreDestroy
    public void cleanup() {
        executor.shutdownNow();
    }
}