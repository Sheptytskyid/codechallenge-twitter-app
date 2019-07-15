package com.codechallenge.twitterapp.controller;

import com.codechallenge.twitterapp.dto.UserConnectionDto;
import com.codechallenge.twitterapp.exception.InvalidUserRequestException;
import com.codechallenge.twitterapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static java.lang.String.format;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId1}/follow/{userId2}")
    public ResponseEntity<String> followUser(@PathVariable("userId1") long userId1, @PathVariable("userId2") long userId2) {
        try {
            LOG.debug(format("Executing request of userId %d to follow userId %d", userId1, userId2));
            UserConnectionDto followRequest = new UserConnectionDto(userId1, userId2);
            userService.followUser(followRequest);
            return new ResponseEntity<>(userId1 + " is now following " + userId2, HttpStatus.OK);
        } catch (InvalidUserRequestException e) {
            LOG.error(format("Failed to execute request of userId %d to follow userId %d", userId1, userId2));
            LOG.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/{userId1}/unfollow/{userId2}")
    public ResponseEntity<String> unFollowUser(@PathVariable("userId1") long userId1, @PathVariable("userId2") long userId2) {
        try {
            LOG.debug(format("Executing request of userId %d to unfollow userId %d", userId1, userId2));
            UserConnectionDto followRequest = new UserConnectionDto(userId1, userId2);
            userService.unFollowUser(followRequest);
            return new ResponseEntity<>(userId1 + " has unfollowed " + userId2, HttpStatus.OK);
        } catch (InvalidUserRequestException e) {
            LOG.error(format("Failed to execute request of userId %d to unfollow userId %d", userId1, userId2));
            LOG.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
