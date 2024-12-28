package com.example.shared.runner;

import com.example.shared.service.MessageDistributionTracker;
import com.example.shared.service.SharedTopicPublisher;
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
public class SharedSubscriptionDemoRunner implements ApplicationRunner {
    private final Logger log = LoggerFactory.getLogger(SharedSubscriptionDemoRunner.class);
    private final SharedTopicPublisher publisher;
    private final MessageDistributionTracker distributionTracker;
    private final ScheduledExecutorService executor;

    @Autowired
    public SharedSubscriptionDemoRunner(
            SharedTopicPublisher publisher,
            MessageDistributionTracker distributionTracker) {
        this.publisher = publisher;
        this.distributionTracker = distributionTracker;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        demonstrateSharedSubscription();
    }

    private void demonstrateSharedSubscription() throws InterruptedException {
        System.out.println("\n=== Demonstrating shared subscription Messaging ===");

        // Send messages to demonstrate load distribution
        log.info("Sending messages to shared subscription...");
        for (int i = 1; i < 10; i++) {
            publisher.publishMessage("distributed message");
            Thread.sleep(500); // Small delay between messages
        }

        // Wait a bit for all messages to be processed
        Thread.sleep(1000);

        // Generate and display the distribution report
        String report = distributionTracker.generateDistributionReport();
        System.out.println(report);
        log.info("Demonstration complete - see distribution report above");

        executor.shutdown();
    }

    @PreDestroy
    public void cleanup() {
        executor.shutdownNow();
    }
}