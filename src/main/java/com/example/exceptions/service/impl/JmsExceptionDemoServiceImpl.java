package com.example.exceptions.service.impl;

import com.example.exceptions.exception.JmsExceptionHandler;
import com.example.exceptions.service.JmsExceptionDemoService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class JmsExceptionDemoServiceImpl implements JmsExceptionDemoService {

    private final JmsExceptionHandler exceptionHandler;
    private final ConnectionFactory connectionFactory;

    @Autowired
    public JmsExceptionDemoServiceImpl(JmsExceptionHandler exceptionHandler,
                                       ConnectionFactory connectionFactory) {
        this.exceptionHandler = exceptionHandler;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void demonstrateInvalidDestination() {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a message first
            TextMessage message = session.createTextMessage("Test message");

            // Try to send to a non-existent temporary queue that has been deleted
            TemporaryQueue tempQueue = session.createTemporaryQueue();
            MessageProducer producer = session.createProducer(tempQueue);

            // Delete the temporary queue while the producer is still bound to it
            tempQueue.delete();

            // Attempting to send to the deleted queue will throw InvalidDestinationException
            producer.send(message);

        } catch (InvalidDestinationException e) {
            exceptionHandler.handleInvalidDestination(e);
        } catch (JMSException e) {
            exceptionHandler.handleUnexpectedException(e);
        } finally {
            closeResources(session, connection);
        }
    }

    @Override
    public void demonstrateMessageFormat() {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            StreamMessage message = session.createStreamMessage();
            message.writeDouble(123.45);

            // Reset the stream to read from the beginning
            message.reset();

            // Try to read a double as a boolean
            // This should throw MessageFormatException
            boolean value = message.readBoolean();

        } catch (MessageFormatException e) {
            exceptionHandler.handleMessageFormat(e);
        } catch (JMSException e) {
            exceptionHandler.handleUnexpectedException(e);
        } finally {
            closeResources(session, connection);
        }
    }

    @Override
    public void demonstrateMessageNotWriteable() {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a temporary queue for this demonstration
            Queue tempQueue = session.createTemporaryQueue();

            // First, send a message to the queue
            MessageProducer producer = session.createProducer(tempQueue);
            TextMessage message = session.createTextMessage("Original Text");
            producer.send(message);

            // Receive the message - this makes it read-only
            MessageConsumer consumer = session.createConsumer(tempQueue);
            TextMessage receivedMessage = (TextMessage) consumer.receive(1000);

            // Try to modify the received message - this will throw MessageNotWriteableException
            receivedMessage.setText("New Text");

        } catch (MessageNotWriteableException e) {
            exceptionHandler.handleMessageNotWriteable(e);
        } catch (JMSException e) {
            exceptionHandler.handleUnexpectedException(e);
        } finally {
            closeResources(session, connection);
        }
    }

    @Override
    public void demonstrateIllegalState() {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            // Close the session first
            session.close();

            // Try to create a message using the closed session
            // This will throw IllegalStateException
            Message message = session.createMessage();

        } catch (javax.jms.IllegalStateException e) {
            exceptionHandler.handleIllegalState(e);
        } catch (JMSException e) {
            exceptionHandler.handleUnexpectedException(e);
        } finally {
            closeResources(session, connection);
        }
    }

    @Override
    public void demonstrateJmsSecurity() {
        Connection secureConnection = null;
        try {
            // Create a new connection factory with invalid credentials
            ActiveMQConnectionFactory invalidFactory = new ActiveMQConnectionFactory();
            invalidFactory.setBrokerURL("vm://embedded-broker?create=false");
            invalidFactory.setUserName("invalid");
            invalidFactory.setPassword("invalid");

            // This will throw JMSSecurityException
            secureConnection = invalidFactory.createConnection();
            secureConnection.start();

        } catch (JMSSecurityException e) {
            exceptionHandler.handleJmsSecurity(e);
        } catch (JMSException e) {
            exceptionHandler.handleUnexpectedException(e);
        } finally {
            closeConnection(secureConnection);
        }
    }

    private void closeResources(Session session, Connection connection) {
        try {
            if (session != null) {
                session.close();
            }
        } catch (JMSException e) {
            exceptionHandler.handleUnexpectedException(e);
        }
        closeConnection(connection);
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            exceptionHandler.handleUnexpectedException(e);
        }
    }
}