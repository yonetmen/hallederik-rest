package com.kasimgul.domain;

import java.util.Date;

public class Comment {

    private String authorsUsername;
    private Date createdAt;
    private String bodyText;

    public Comment() {
    }

    public Comment(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getAuthorsUsername() {
        return authorsUsername;
    }

    public void setAuthorsUsername(String authorsUsername) {
        this.authorsUsername = authorsUsername;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    @Override
    public String toString() {
        return "Comment{" +
                ", authorsUsername='" + authorsUsername + '\'' +
                ", createdAt=" + createdAt +
                ", bodyText='" + bodyText + '\'' +
                '}';
    }
}
