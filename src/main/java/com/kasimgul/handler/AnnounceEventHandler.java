package com.kasimgul.handler;

import com.kasimgul.domain.Announce;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
@RepositoryEventHandler(Announce.class)
public class AnnounceEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreateAnnounce(Announce announce) {
        System.out.println("Announce HandleBeforeCreate called");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        announce.setOwnerUsername(username);
        announce.setCreatedAt(new Date());
        announce.setActive(true);
        announce.setCommentList(new ArrayList<>());
    }
}
