package com.example.browser.service;

import javax.jms.*;

import com.example.browser.config.JmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

@Service
public class QueueBrowserService {
    private final ConnectionFactory connectionFactory;

    @Autowired
    public QueueBrowserService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void browseMessages() throws JMSException {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(JmsConfig.QUEUE_NAME);

            QueueBrowser browser = session.createBrowser(queue);
            connection.start();

            Enumeration<?> messageEnum = browser.getEnumeration();

            System.out.println("\nBrowsing messages in the queue:");
            int messageCount = 0;

            while (messageEnum.hasMoreElements()) {
                Message message = (Message) messageEnum.nextElement();
                messageCount++;

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Message " + messageCount + ": " + textMessage.getText());
                    System.out.println("Index: " + message.getIntProperty("messageIndex"));
                    System.out.println("JMSMessageID: " + message.getJMSMessageID());
                    System.out.println("JMSTimestamp: " + message.getJMSTimestamp());
                    System.out.println("---");
                }
            }

            System.out.println("Total messages in queue: " + messageCount);
        }
    }

    public void browseMessagesWithSelector(String selector) throws JMSException {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(JmsConfig.QUEUE_NAME);

            QueueBrowser browser = session.createBrowser(queue, selector);
            connection.start();

            Enumeration<?> messageEnum = browser.getEnumeration();

            System.out.println("\nBrowsing messages with selector: " + selector);
            int messageCount = 0;

            while (messageEnum.hasMoreElements()) {
                Message message = (Message) messageEnum.nextElement();
                messageCount++;

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Message " + messageCount + ": " + textMessage.getText());
                    System.out.println("Index: " + message.getIntProperty("messageIndex"));
                }
            }

            System.out.println("Total matching messages: " + messageCount);
        }
    }

    public int getMessageCount() throws JMSException {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(JmsConfig.QUEUE_NAME);

            QueueBrowser browser = session.createBrowser(queue);
            connection.start();

            Enumeration<?> messageEnum = browser.getEnumeration();
            int count = 0;
            while (messageEnum.hasMoreElements()) {
                messageEnum.nextElement();
                count++;
            }
            return count;
        }
    }
}
