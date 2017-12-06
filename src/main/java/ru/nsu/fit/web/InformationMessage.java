package ru.nsu.fit.web;

import java.io.Serializable;

public class InformationMessage implements Serializable{
    private String message;

    public InformationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
