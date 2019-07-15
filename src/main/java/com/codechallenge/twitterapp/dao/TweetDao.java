package com.codechallenge.twitterapp.dao;

import com.codechallenge.twitterapp.model.Tweet;
import com.codechallenge.twitterapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TweetDao extends JpaRepository<Tweet, Long> {

    List<Tweet> findByAuthorOrderByTimeStampDesc(User author);

    List<Tweet> findByAuthorIdInOrderByTimeStampDesc(Set<Long> authors);
}
