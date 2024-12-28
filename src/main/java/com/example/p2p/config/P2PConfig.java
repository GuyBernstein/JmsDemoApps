package com.example.p2p.config;

import com.example.p2p.sender.OrderSender;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P2PConfig {
    @Bean
    ApplicationRunner demoP2P(OrderSender sender) {
        return args -> {
            System.out.println("\n=== Demonstrating Point-to-Point Messaging ===");
            System.out.println("Sending orders to processing queue...\n");

            for (int i = 1; i <= 3; i++) {
                sender.sendOrder("Order #" + i + " - Process payment", i);
                Thread.sleep(1000);
            }
        };
    }
}
