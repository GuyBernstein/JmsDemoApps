package com.example.durable.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

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
    public SingleConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("vm://embedded-broker");
        SingleConnectionFactory factory = new SingleConnectionFactory(activeMQConnectionFactory);
        // Set a consistent client ID for durable subscriptions
        factory.setClientId("durableDemo");
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> durableTopicListenerFactory(SingleConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        // Enable automatic recovery of connections
        factory.setAutoStartup(true);
        factory.setSessionTransacted(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(SingleConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }
}