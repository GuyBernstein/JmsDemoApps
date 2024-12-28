package com.example.exceptions.runner;


import com.example.exceptions.service.JmsExceptionDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JmsExceptionDemoRunner implements CommandLineRunner {

    private final JmsExceptionDemoService demoService;
    private static final Logger logger = LoggerFactory.getLogger(JmsExceptionDemoRunner.class);

    public JmsExceptionDemoRunner(JmsExceptionDemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting JMS Exceptions Demo...\n");

        demonstrateWithSeparator("InvalidDestination", demoService::demonstrateInvalidDestination);
        demonstrateWithSeparator("MessageFormat", demoService::demonstrateMessageFormat);
        demonstrateWithSeparator("MessageNotWriteable", demoService::demonstrateMessageNotWriteable);
        demonstrateWithSeparator("IllegalState", demoService::demonstrateIllegalState);
        demonstrateWithSeparator("JmsSecurity", demoService::demonstrateJmsSecurity);

        System.out.println("\nJMS Exceptions Demo completed.");
    }

    private void demonstrateWithSeparator(String demoName, Runnable demonstration) {
        System.out.println("\n========== Demonstrating " + demoName + " ==========");
        try {
            demonstration.run();
        } catch (Exception e) {
            logger.error("Error during {} demonstration: {}", demoName, e.getMessage());
        }
        System.out.println("========== End of " + demoName + " Demo ==========\n");

        // Add a small delay between demonstrations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
