package com.codechallenge.twitterapp.service;

import com.codechallenge.twitterapp.dao.UserDao;
import com.codechallenge.twitterapp.exception.InvalidUserRequestException;
import com.codechallenge.twitterapp.model.User;
import com.codechallenge.twitterapp.dto.UserConnectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static java.lang.String.format;

@Service
@Transactional
public class UserProcessor implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserProcessor.class);
    private UserDao userDao;

    @Autowired
    public UserProcessor(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void followUser(UserConnectionDto request) {
        validateRequest(request);
        User mainUser = findOrCreateUser(request.getPrimaryUserId());
        User followedUser = findOrCreateUser(request.getFollowedUserId());
        mainUser.getFollowingIds().add(followedUser.getId());
        userDao.save(mainUser);
        LOG.debug(format("UserId %d followed userId %d", request.getPrimaryUserId(), request.getFollowedUserId() ));
    }

    @Override
    public void unFollowUser(UserConnectionDto request) {
        validateRequest(request);
        User mainUser = findOrCreateUser(request.getPrimaryUserId());
        User followedUser = findOrCreateUser(request.getFollowedUserId());
        mainUser.getFollowingIds().remove(followedUser.getId());
        userDao.save(mainUser);
        LOG.debug(format("UserId %d unfollowed userId %d", request.getPrimaryUserId(), request.getFollowedUserId() ));
    }

    @Override
    public User findOrCreateUser(Long userId) {
        LOG.debug("Searching for userId: " + userId);
        return userDao.findById(userId).orElseGet(() -> createUser(userId));
    }

    @Override
    public User createUser(Long userId) {
        User user = new User(userId, "User" + userId, new HashSet<>());
        LOG.info("Creating user: " + user);
        return userDao.save(user);
    }

    private void validateRequest(UserConnectionDto request) {
        String errorMessage = null;
        if (request == null) {
            errorMessage = "User connection request is null";
        } else if (request.getFollowedUserId() == null || request.getFollowedUserId() < 0) {
            errorMessage = "Invalid id of user to follow: " + request.getFollowedUserId();
        } else if (request.getPrimaryUserId() == null || request.getPrimaryUserId() < 0) {
            errorMessage = "Invalid user Id: " + request.getPrimaryUserId();
        } else if (request.getPrimaryUserId().equals(request.getFollowedUserId())) {
            errorMessage = "User cannot follow him/herself";
        }
        if (errorMessage != null) {
            LOG.error("User connection request has validation errors: " + errorMessage);
            throw new InvalidUserRequestException(errorMessage);
        }
    }
}
