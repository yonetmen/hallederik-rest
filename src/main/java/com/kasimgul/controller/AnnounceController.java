package com.kasimgul.controller;

import com.kasimgul.domain.Announce;
import com.kasimgul.domain.Comment;
import com.kasimgul.domain.Message;
import com.kasimgul.domain.User;
import com.kasimgul.exception.ConstraintValidationException;
import com.kasimgul.exception.ResourceNotFoundException;
import com.kasimgul.repository.AnnounceRepository;
import com.kasimgul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.core.event.BeforeCreateEvent;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@RepositoryRestController
public class AnnounceController implements ApplicationEventPublisherAware {

    @Autowired
    AnnounceRepository announceRepository;
    @Autowired
    UserRepository userRepository;

    private ApplicationEventPublisher publisher;

    @RequestMapping(value = "/announces/{announceId}", method = RequestMethod.GET)
    public ResponseEntity<Announce> getAnnounceById(@PathVariable("announceId") String announceId) {
        verifyResourceExist(announceId);
        Announce announce = announceRepository.findOne(announceId);
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    // TESTING
    @RequestMapping(value = "/announces", method = RequestMethod.POST)
    public ResponseEntity<Announce> createAnnounce(@RequestBody Announce announce) {
        Announce a = verifyValidation(announce);
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    } // TESTING

    @RequestMapping(value = "/announces/{announceId}", method = RequestMethod.PUT)
    public ResponseEntity<Announce> getAll(@PathVariable("announceId") String announceId, @RequestBody Announce updated) {
        Announce announce = verifyResourceExist(announceId);
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean isAdmin = isUserAdmin(user);

        if (!user.getUsername().equals(announce.getOwnerUsername()) && !isAdmin) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (updated.getCategory() != null && !updated.getCategory().isEmpty())
            announce.setCategory(updated.getCategory());
        if (updated.getBody() != null && !updated.getBody().isEmpty())
            announce.setBody(updated.getBody());
        if (updated.getCity() != null && !updated.getCity().isEmpty())
            announce.setCity(updated.getCity());
        if (updated.getState() != null && !updated.getState().isEmpty())
            announce.setState(updated.getState());

        announceRepository.save(announce);
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    @RequestMapping(value = "/announces/{announceId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("announceId") String announceId) {
        Announce announce = verifyResourceExist(announceId);
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean isAdmin = isUserAdmin(user);
        if (!user.getUsername().equals(announce.getOwnerUsername()) && !isAdmin) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        announceRepository.delete(announce);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean isUserAdmin(User user) {
        String[] roles = user.getRoles();
        for (String role : roles) {
            if (role.contains("ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private Announce verifyResourceExist(String announceId) throws ResourceNotFoundException {
        Announce announce = announceRepository.findOne(announceId);
        if (announce == null) {
            throw new ResourceNotFoundException("Announce with ID: " + announceId + " not found.");
        }
        return announce;
    }

    private Announce verifyValidation(Announce announce) throws RepositoryConstraintViolationException {
        try{
            publisher.publishEvent(new BeforeCreateEvent(announce));
            announceRepository.save(announce);
        } catch (RepositoryConstraintViolationException ex) {
            throw new RepositoryConstraintViolationException(ex.getErrors());
        }
        return announce;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    // Adding a new Comment on the announce. This URI has be hooked to the post comment form and redirect to announce page
    @RequestMapping(value = "/announces/{announceId}/comments", method = RequestMethod.POST)
    public ResponseEntity<Announce> createComment(@PathVariable("announceId") String announceId, @RequestBody Comment comment) {
        Announce announce = verifyResourceExist(announceId);

        if(comment.getBodyText() == null || comment.getBodyText().length() < 2 || comment.getBodyText().length() > 500) {
            throw new ConstraintValidationException("Comment length must be between 2 and 500 characters");
        }
        comment.setAuthorsUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setCreatedAt(new Date());
        announce.getCommentList().add(comment);
        announceRepository.save(announce);
        return new ResponseEntity<>(announce, HttpStatus.CREATED);
    }

    // Todo: Working but when an Announce is deleted, all related messages from all user documents must be removed as well.
    @RequestMapping(value = "/announces/{announceId}/messages", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@PathVariable("announceId") String announceId, @RequestBody Message message) {
        Announce announce = verifyResourceExist(announceId);
        if(message.getMessageBody() == null || message.getMessageBody().length() < 3 || message.getMessageBody().length() > 255) {
            throw new ConstraintValidationException("Message text must be between 2 and 255 characters");
        }
        String receiverUsername = announce.getOwnerUsername();
        String senderUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User senderUser = userRepository.findByUsername(senderUsername);
        User receiverUser = userRepository.findByUsername(receiverUsername);

        message.setSenderUsername(senderUsername);
        message.setReceiverUsername(receiverUsername);
        message.setCreatedAt(new Date());
        message.setAnnounceId(announceId);

        senderUser.getMessageList().add(message);
        receiverUser.getMessageList().add(message);

        userRepository.save(receiverUser);
        userRepository.save(senderUser);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
