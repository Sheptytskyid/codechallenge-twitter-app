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
public class TweetDto {

    final Long userId;
    final String text;

    public TweetDto() {
        userId = null;
        text = null;
    }

}
