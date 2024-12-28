package com.example.types.runner;

import com.example.types.model.CustomObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

import static com.example.types.model.CustomObject.CustomObjectBuilder.aCustomObject;

@Component
public class JmsMessageTypesRunner implements ApplicationRunner {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsMessageTypesRunner(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Starting JMS Message Types Demo...\n");

        // 1. TextMessage
        sendTextMessage();
        sleep();


        // 2. MapMessage
        sendMapMessage();
        sleep();

        // 3. BytesMessage
        sendBytesMessage();
        sleep();

        // 4. StreamMessage
        sendStreamMessage();
        sleep();

        // 5. ObjectMessage
        sendObjectMessage();
        sleep();

        // 6. Message (Empty)
        sendEmptyMessage();
    }

    private void sendTextMessage() {
        String destination = "text.message.queue";
        String text = "<?xml version=\"1.0\"?><message>Hello from TextMessage!</message>";

        jmsTemplate.send(destination, session -> {
            TextMessage message = session.createTextMessage(text);
            message.setStringProperty("messageType", "TextMessage");
            return message;
        });

        System.out.println("Sent TextMessage to " + destination);
    }

    private void sendMapMessage() {
        String destination = "map.message.queue";

        jmsTemplate.send(destination, session -> {
            MapMessage message = session.createMapMessage();
            message.setString("name", "Guy Bernstein");
            message.setInt("age", 26);
            message.setBoolean("employed", false);
            message.setStringProperty("messageType", "MapMessage");
            return message;
        });

        System.out.println("Sent MapMessage to " + destination);
    }

    private void sendBytesMessage() {
        String destination = "bytes.message.queue";
        String content = "This is a BytesMessage content";
        byte[] bytes = content.getBytes();

        jmsTemplate.send(destination, session -> {
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(bytes);
            message.setStringProperty("messageType", "BytesMessage");
            message.setIntProperty("contentLength", bytes.length);
            return message;
        });

        System.out.println("Sent BytesMessage to " + destination);
    }


    private void sendStreamMessage() {
        String destination = "stream.message.queue";

        jmsTemplate.send(destination, session -> {
            StreamMessage message = session.createStreamMessage();
            message.writeString("String value");
            message.writeInt(42);
            message.writeBoolean(true);
            message.writeDouble(3.14);
            message.setStringProperty("messageType", "StreamMessage");
            return message;
        });

        System.out.println("Sent StreamMessage to " + destination);
    }

    private void sendObjectMessage() {
        String destination = "object.message.queue";
        CustomObject customObject = aCustomObject()
                .age(26)
                .name("Guy Bernstein")
                .build();

        jmsTemplate.send(destination, session -> {
            ObjectMessage message = session.createObjectMessage(customObject);
            message.setStringProperty("messageType", "ObjectMessage");
            return message;
        });

        System.out.println("Sent ObjectMessage to " + destination);
    }

    private void sendEmptyMessage() {
        String destination = "empty.message.queue";

        jmsTemplate.send(destination, session -> {
            Message message = session.createMessage();
            message.setStringProperty("messageType", "EmptyMessage");
            message.setIntProperty("priority", 1);
            message.setBooleanProperty("body", false);
            return message;
        });

        System.out.println("Sent Empty Message to " + destination);
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}