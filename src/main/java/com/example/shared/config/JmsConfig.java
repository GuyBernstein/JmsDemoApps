package com.example.shared.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQTopic;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

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
        return broker;
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL("vm://embedded-broker?create=false");

        // Enable virtual topics
        activeMQConnectionFactory.setTrustedPackages(List.of("com.example.shared"));

        CachingConnectionFactory factory = new CachingConnectionFactory(activeMQConnectionFactory);
        factory.setClientId("sharedClient");
        return factory;
    }

    @Bean
    public ActiveMQTopic topic() {
        return new ActiveMQTopic("VirtualTopic.sharedTopic");
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(CachingConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false); // Set to false for queue-based consumption
        factory.setSessionTransacted(true);
        factory.setConcurrency("1-3"); // Allow up to 3 concurrent consumers
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true); // Keep true for publishing to topics
        return jmsTemplate;
    }
}