package com.example.selector.model;

import java.io.Serializable;

public class NewsMessage implements Serializable {
    private String type;

    private String content;
    private String priority;

    public NewsMessage() {
    }

    public NewsMessage(String type, String content, String priority) {
        this.type = type;
        this.content = content;
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }



    @Override
    public String toString() {
        return "NewsMessage{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
