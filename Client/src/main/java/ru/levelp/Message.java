package ru.levelp;

/**
 * Created by Юрий on 21.09.2016.
 */
public class Message {
    private String sender;
    private String receiver;
    private String message;

    public Message() {}

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}