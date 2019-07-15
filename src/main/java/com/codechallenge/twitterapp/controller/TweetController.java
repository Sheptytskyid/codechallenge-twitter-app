package com.codechallenge.twitterapp.controller;

import com.codechallenge.twitterapp.dto.TweetDto;
import com.codechallenge.twitterapp.exception.InvalidTweetRequestException;
import com.codechallenge.twitterapp.exception.InvalidUserRequestException;
import com.codechallenge.twitterapp.model.Tweet;
import com.codechallenge.twitterapp.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private static final Logger LOG = LoggerFactory.getLogger(TweetController.class);
    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/")
    public ResponseEntity<List<Tweet>> createTweet(@RequestBody TweetDto dto) {
        try {
            LOG.debug("Creating new tweet from request: " + dto);
            List<Tweet> tweets = tweetService.addTweet(dto);
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        } catch (InvalidTweetRequestException e) {
            LOG.error("Failed to create tweet from request:" + dto);
            LOG.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/by/{userId}")
    public ResponseEntity<List<Tweet>> findTweetsOfUser(@PathVariable("userId") Long userId) {
        try {
            LOG.debug("Finding tweets of userId: " + userId);
            List<Tweet> tweets = tweetService.findTweetsOfUser(userId);
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        } catch (InvalidUserRequestException e) {
            LOG.error("Failed to load tweets of userId: " + userId);
            LOG.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/for/{userId}")
    public ResponseEntity<List<Tweet>> findTweetsForUser(@PathVariable("userId") Long userId) {
        try {
            LOG.debug("Finding tweets of followers for userId: " + userId);
            List<Tweet> tweets = tweetService.findTweetsOfFollowedUsers(userId);
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        } catch (InvalidUserRequestException e) {
            LOG.error("Failed to load tweets of followers for userId: " + userId);
            LOG.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
