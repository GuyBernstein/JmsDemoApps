package com.example.types.model;

import java.io.Serializable;

public class CustomObject implements Serializable {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "CustomObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final class CustomObjectBuilder {
        private String name;
        private int age;

        private CustomObjectBuilder() {
        }

        public static CustomObjectBuilder aCustomObject() {
            return new CustomObjectBuilder();
        }

        public CustomObjectBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomObjectBuilder age(int age) {
            this.age = age;
            return this;
        }

        public CustomObject build() {
            CustomObject customObject = new CustomObject();
            customObject.setName(name);
            customObject.setAge(age);
            return customObject;
        }
    }
}
