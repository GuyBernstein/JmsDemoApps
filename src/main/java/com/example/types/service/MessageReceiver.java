package com.example.types.service;

import com.example.types.model.CustomObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class MessageReceiver {

    @JmsListener(destination = "text.message.queue")
    public void receiveTextMessage(TextMessage message) throws JMSException {
        System.out.println("\nReceived TextMessage:");
        System.out.println("Content: " + message.getText());
        System.out.println("Type: " + message.getStringProperty("messageType"));
    }

    @JmsListener(destination = "map.message.queue")
    public void receiveMapMessage(MapMessage message) throws JMSException {
        System.out.println("\nReceived MapMessage:");
        System.out.println("Name: " + message.getString("name"));
        System.out.println("Age: " + message.getInt("age"));
        System.out.println("Employed: " + message.getBoolean("employed"));
        System.out.println("Type: " + message.getStringProperty("messageType"));
    }

    @JmsListener(destination = "bytes.message.queue")
    public void receiveBytesMessage(BytesMessage message) throws JMSException {
        System.out.println("\nReceived BytesMessage:");
        byte[] bytes = new byte[(int) message.getBodyLength()];
        message.readBytes(bytes);
        System.out.println("Content as String: " + new String(bytes));

        // Print bytes in binary format
        System.out.println("Content as Binary: ");
        for (byte b : bytes) {
            // Convert byte to binary string, padding with leading zeros to ensure 8 bits
            String binary = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            System.out.print(binary + " ");
        }
        System.out.println(); // New line after binary representation

        System.out.println("Content Length: " + message.getIntProperty("contentLength"));
        System.out.println("Type: " + message.getStringProperty("messageType"));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @JmsListener(destination = "stream.message.queue")
    public void receiveStreamMessage(StreamMessage message) throws JMSException {
        System.out.println("\nReceived StreamMessage:");
        System.out.println("String value: " + message.readString());
        System.out.println("Integer value: " + message.readInt());
        System.out.println("Boolean value: " + message.readBoolean());
        System.out.println("Double value: " + message.readDouble());
        System.out.println("Type: " + message.getStringProperty("messageType"));
    }

    @JmsListener(destination = "object.message.queue")
    public void receiveObjectMessage(ObjectMessage message) throws JMSException {
        System.out.println("\nReceived ObjectMessage:");
        CustomObject obj = (CustomObject) message.getObject();
        System.out.println("Custom Object: " + obj);
        System.out.println("Type: " + message.getStringProperty("messageType"));
    }

    @JmsListener(destination = "empty.message.queue")
    public void receiveEmptyMessage(Message message) throws JMSException {
        System.out.println("\nReceived Empty Message:");
        System.out.println("Priority: " + message.getIntProperty("priority"));
        System.out.println("body: " + message.getBooleanProperty("processed"));
        System.out.println("Type: " + message.getStringProperty("messageType"));
    }
}
