package com.codechallenge.twitterapp.dao;

import com.codechallenge.twitterapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);

    Optional<User> findByName(String name);
}
