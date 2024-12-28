package com.example.p2p.model;


import java.io.Serializable;

public class Message implements Serializable {
    private String content;
    private int messageNumber;

    // Default constructor for Jackson
    public Message() {}

    public Message(String content, int messageNumber) {
        this.content = content;
        this.messageNumber = messageNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    @Override
    public String toString() {
        return "Message{content='" + content + "', messageNumber=" + messageNumber + '}';
    }
}