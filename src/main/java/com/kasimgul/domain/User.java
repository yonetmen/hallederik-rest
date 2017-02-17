package com.kasimgul.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class User {

    @Id
    private String id;
    @NotNull(message = "{user.username.notnull}")
    @Size(min = 4, max = 25, message = "{user.username.size}")
    @Indexed(unique = true)
    private String username;
    @NotNull(message = "{user.password.notnull}")
    @Size(min = 6, message = "{user.password.size}")
    @JsonIgnore
    private String password;
    @NotNull(message = "{user.email.notnull}")
    @Email(message = "{user.email.format}")
    @Indexed(unique = true)
    private String email;
    private String[] roles;
    private Date createdAt;
    private List<Message> messageList;
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        setPassword(password);
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", messages=" + messageList +
                '}';
    }
}
