package com.codechallenge.twitterapp.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserConnectionDto {
    final Long primaryUserId;
    final Long followedUserId;

    public UserConnectionDto() {
        primaryUserId = null;
        followedUserId = null;
    }
}
