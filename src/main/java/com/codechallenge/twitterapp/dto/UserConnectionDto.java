package com.codechallenge.twitterapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserConnectionDto {
    private final Long primaryUserId;
    private final Long followedUserId;

    public UserConnectionDto() {
        primaryUserId = null;
        followedUserId = null;
    }
}
