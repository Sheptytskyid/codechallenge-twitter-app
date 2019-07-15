Feature: Viewing tweets via tweet rest api

  Background:
    Given User1 exists in the system
    And User2 exists in the system
    And User3 exists in the system

  Scenario: 1-Users should not be able to post messages longer than 140 chars long
    When User1 posts a tweet of 150 chars
    Then System returns error massage: Message cannot have more than 140 characters

  Scenario: 2-Users should not be able to post empty messages
    When User1 posts a tweet of 0 chars
    Then System returns error massage: Message cannot be empty

  Scenario: 3-Users should be able to post messages less than 140 chars long
    When User1 posts a tweet of 140 chars
    Then 1 tweet is saved in the system

  Scenario: 4-Users should be able to post identical messages
    When User1 posts the tweet: Message1
    And User1 posts the tweet: Message1
    Then 2 tweets are saved in the system

  Scenario: 5-Users should be able to see their messages in reverse chronological order
    When User1 posts the tweet: Message1
    And User1 posts the tweet: Message2
    And User1 views his tweets
    Then 2 tweets are displayed
    And Tweets appear in reverse chronological order

  Scenario: 6-Users should be able to see messages of the other users whom they follow in reverse chronological order
    When User1 is following User2
    And User1 is following User3
    And User2 posts the tweet: Message2-1
    And User2 posts the tweet: Message2-2
    And User3 posts the tweet: Message3-1
    And User1 views tweets of the users that he follows
    Then 3 tweets are displayed
    And Tweets appear in reverse chronological order

  Scenario: 7-Users should not be able to see messages of the other users whom they do not follow
    When User1 is following User2
    And User3 posts the tweet: Message3-1
    And User1 views tweets of the users that he follows
    Then 0 tweets are displayed
