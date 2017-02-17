package com.kasimgul.controller;

import com.kasimgul.domain.User;
import com.kasimgul.exception.MongoDbDuplicateKeyException;
import com.kasimgul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.core.event.BeforeCreateEvent;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RepositoryRestController
public class UserController implements ApplicationEventPublisherAware {

    private static final String NEW_USER = "new";
    private static final String UPDATED_USER = "update";

    @Autowired
    private UserRepository userRepository;

    private ApplicationEventPublisher publisher;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userRepository.findAll();
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User u = verifyValidation(user, NEW_USER);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #updated.username.equals(authentication.name)")
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody User updated) {
        User u = verifyValidation(updated, UPDATED_USER);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }


    private User verifyValidation(User user, String status) throws RepositoryConstraintViolationException {
        try {
            if (status.equals(NEW_USER))
                publisher.publishEvent(new BeforeCreateEvent(user));
            return userRepository.save(user);
        } catch (RepositoryConstraintViolationException ex) {
            throw new RepositoryConstraintViolationException(ex.getErrors());
        } catch (RuntimeException e) {
            String localizedMessage = e.getLocalizedMessage();
            System.out.println(e.getLocalizedMessage());
            String exceptionMessage;
            if (localizedMessage.contains("username"))
                exceptionMessage = "Duplicate username is not allowed";
            else if (localizedMessage.contains("email"))
                exceptionMessage = "Duplicate email is not allowed";
            else
                exceptionMessage = e.getLocalizedMessage();
            throw new MongoDbDuplicateKeyException(exceptionMessage);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
