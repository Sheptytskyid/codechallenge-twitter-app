package com.codechallenge.twitterapp.service;

import com.codechallenge.twitterapp.controller.UserController;
import com.codechallenge.twitterapp.dao.TweetDao;
import com.codechallenge.twitterapp.exception.InvalidTweetRequestException;
import com.codechallenge.twitterapp.model.Tweet;
import com.codechallenge.twitterapp.model.User;
import com.codechallenge.twitterapp.dto.TweetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TweetProcessor implements TweetService {

    private static final Logger LOG = LoggerFactory.getLogger(TweetProcessor.class);
    private TweetDao tweetDao;
    private UserService userService;

    @Autowired
    public TweetProcessor(TweetDao tweetDao, UserService userService) {
        this.tweetDao = tweetDao;
        this.userService = userService;
    }

    @Override
    public List<Tweet> findTweetsOfUser(Long userId) {
        User author = userService.findOrCreateUser(userId);
        List<Tweet> tweets = tweetDao.findByAuthorOrderByTimeStampDesc(author);
        LOG.debug("Tweets of userId: " + userId + tweets);
        return tweets;
    }

    @Override
    public List<Tweet> findTweetsOfFollowedUsers(Long userId) {
        User author = userService.findOrCreateUser(userId);
        List<Tweet> tweets = tweetDao.findByAuthorIdInOrderByTimeStampDesc(author.getFollowingIds());
        LOG.debug("Tweets of users followed by userId: " + userId + tweets);
        return tweets;
    }

    @Override
    public List<Tweet> addTweet(TweetDto request) {
        validateTweetDto(request);
        User author = userService.findOrCreateUser(request.getUserId());
        Tweet tweet = new Tweet(request.getText(), LocalDateTime.now(), author);
        tweetDao.save(tweet);
        LOG.debug("Created tweet: " + tweet);
        return findTweetsOfUser(author.getId());
    }

    private void validateTweetDto(TweetDto request) {
        String errorMessage = null;
        if (request == null) {
            errorMessage = "Tweet request is null";
        } else {
            if (request.getText().length() > 140) {
                errorMessage = "Message cannot have more than 140 characters";
            } else if (request.getText().isEmpty()) {
                errorMessage = "Message cannot be empty";
            }
        }
        if (errorMessage != null) {
            LOG.error("User connection request has validation errors: " + errorMessage);
            throw new InvalidTweetRequestException(errorMessage);
        }
    }
}
