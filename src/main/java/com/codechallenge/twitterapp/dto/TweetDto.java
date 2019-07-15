package com.codechallenge.twitterapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TweetDto {

    private final Long userId;
    private final String text;

    public TweetDto() {
        userId = null;
        text = null;
    }

}
