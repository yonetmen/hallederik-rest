package com.kasimgul.domain;

import java.util.Date;

public class Message {

//    @Id
//    private String id;
    private String senderUsername;
    private String receiverUsername;
//    @NotNull(message = "{message.messageBody.notnull}")
//    @Size(min = 2, max = 255, message = "{message.messageBody.size}")
    private String messageBody;
    private Date createdAt;
    private String announceId;

    public Message() {
    }

    public Message(String messageBody) {
        this.messageBody = messageBody;
    }

    //    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(String announceId) {
        this.announceId = announceId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderUsername='" + senderUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", createdAt=" + createdAt +
                ", announceId='" + announceId + '\'' +
                '}';
    }
}
