package com.codechallenge.twitterapp.service;

import com.codechallenge.twitterapp.model.User;
import com.codechallenge.twitterapp.dto.UserConnectionDto;

public interface UserService {

    void followUser(UserConnectionDto request);
    void unFollowUser(UserConnectionDto request);
    User findOrCreateUser(Long userId);
    User createUser(Long userId);
}
