package com.kasimgul.handler;

import com.kasimgul.domain.User;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    @HandleBeforeCreate(User.class)
    public void handleBeforeCreateUser(User user) {
        System.out.println("User Before Create called");
        user.setCreatedAt(new Date());
        user.setMessageList(new ArrayList<>());
        user.setRoles(new String[]{"ROLE_USER"});
    }
}
