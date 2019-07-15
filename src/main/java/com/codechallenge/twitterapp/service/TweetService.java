package com.codechallenge.twitterapp.service;

import com.codechallenge.twitterapp.model.Tweet;
import com.codechallenge.twitterapp.dto.TweetDto;

import java.util.List;

public interface TweetService {

    List<Tweet> findTweetsOfUser(Long userId);
    List<Tweet> findTweetsOfFollowedUsers(Long userId);
    List<Tweet> addTweet(TweetDto request);
}
