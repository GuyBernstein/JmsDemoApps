package com.example.pubsub.model;


import java.io.Serializable;

public class NewsMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String category;
    private String content;
    private int messageNumber;

    public NewsMessage() {}

    public NewsMessage(String category, String content, int messageNumber) {
        this.category = category;
        this.content = content;
        this.messageNumber = messageNumber;
    }

    // Getters, setters and toString()

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return "NewsMessage{" +
                "category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", messageNumber=" + messageNumber +
                '}';
    }

    public static final class NewsMessageBuilder {
        private String category;
        private String content;
        private int messageNumber;

        private NewsMessageBuilder() {
        }

        public static NewsMessageBuilder aNewsMessage() {
            return new NewsMessageBuilder();
        }

        public NewsMessageBuilder category(String category) {
            this.category = category;
            return this;
        }

        public NewsMessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public NewsMessageBuilder messageNumber(int messageNumber) {
            this.messageNumber = messageNumber;
            return this;
        }

        public NewsMessage build() {
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setCategory(category);
            newsMessage.setContent(content);
            newsMessage.setMessageNumber(messageNumber);
            return newsMessage;
        }
    }
}
