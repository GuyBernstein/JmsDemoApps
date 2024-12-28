package com.example.shared.service;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageDistributionTracker {
    private final List<SharedMessageSubscriber> subscribers;

    public MessageDistributionTracker(List<SharedMessageSubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public String generateDistributionReport() {
        StringBuilder report = new StringBuilder("\n=== Message Distribution Report ===\n");

        int totalMessages = subscribers.stream()
                .mapToInt(SharedMessageSubscriber::getMessageCount)
                .sum();

        subscribers.forEach(subscriber -> {
            double percentage = (subscriber.getMessageCount() * 100.0) / totalMessages;
            report.append(String.format("%s received %d messages (%.1f%%)\n",
                    subscriber.getSubscriberId(),
                    subscriber.getMessageCount(),
                    percentage));
        });

        report.append(String.format("\nTotal messages processed: %d", totalMessages));
        return report.toString();
    }
}

