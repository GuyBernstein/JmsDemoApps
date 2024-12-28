package com.example.exceptions.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JmsConfig {

    @Bean
    public BrokerService broker() throws Exception {
        BrokerService broker = new BrokerService();
        // Enable persistence for durable subscriptions
        broker.setPersistent(false);
        broker.setUseJmx(false);
        broker.setBrokerName("embedded-broker");
        broker.addConnector("vm://embedded-broker");
        // Add a temporary storage location for persistent messages
        broker.setDataDirectory("target/activemq-data");

        // Configure authentication
        SimpleAuthenticationPlugin authenticationPlugin = new SimpleAuthenticationPlugin();

        // Set up users (username, password, groups)
        List<AuthenticationUser> users = new ArrayList<>();
        users.add(new AuthenticationUser("admin", "adminPassword", "admins"));
        authenticationPlugin.setUsers(users);

        // Add the authentication plugin to broker
        broker.setPlugins(new org.apache.activemq.broker.BrokerPlugin[]{authenticationPlugin});

        return broker;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("vm://embedded-broker?create=false");
        factory.setTrustAllPackages(true); // For demo purposes only

        // Set default credentials (these will work)
        factory.setUserName("admin");
        factory.setPassword("adminPassword");
        return factory;
    }
}
