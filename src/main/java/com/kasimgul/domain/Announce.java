package com.kasimgul.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class Announce {

    @Id
    private String id;
    @Version
    private Long version;
    @NotNull(message = "{announce.category.notnull}")
    @Size(min = 3, max = 25, message = "{announce.category.size}")
    @Pattern(regexp = "[a-zA-ZçÇöÖğĞıİşŞüÜ]+", message = "{announce.category.regex}")
    private String category;
    @NotNull(message = "{announce.body.notnull}")
    @Size(min = 15, max = 400, message = "{announce.body.size}")
    private String body;
    @NotNull(message = "{announce.city.notnull}")
    @Size(min = 2, max = 25, message = "{announce.city.size}")
    @Pattern(regexp = "[a-zA-ZçÇöÖğĞıİşŞüÜ]+", message = "{announce.city.regex}")
    private String city;
    @NotNull(message = "{announce.state.notnull}")
    @Size(min = 2, max = 50, message = "{announce.state.size}")
    @Pattern(regexp = "[a-zA-ZçÇöÖğĞıİşŞüÜ]+", message = "{announce.state.regex}")
    private String state;
    private Date createdAt;
    private boolean isActive;
    private List<Comment> commentList;
    private String ownerUsername;

    public Announce() {
    }

    public Announce(String category, String body) {
        this.category = category;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Long getVersion() {
        return version;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    @Override
    public String toString() {
        return "Announce{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", category='" + category + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", isActive=" + isActive +
                ", commentList=" + commentList +
                ", ownerUsername=" + ownerUsername +
                '}';
    }
}
