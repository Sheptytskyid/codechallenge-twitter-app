Feature: Managing user connections via user REST api

  Scenario: 1-Users should be able to follow other users
    Given User1 exists in the system
    And User2 exists in the system
    When User1 sends follow request to User2
    Then User2 is in the followers list of User1
    And User1 is not in the followers list of User2

  Scenario: 2-Users should be able to unfollow other users
    Given User1 exists in the system
    And User2 exists in the system
    And User1 is following User2
    And User2 is following User1
    When User2 sends unfollow request to User1
    Then User2 is in the followers list of User1
    And User1 is not in the followers list of User2

  Scenario: 3-User should not be allowed to follow himself
    Given User1 exists in the system
    When User1 sends follow request to User1
    Then System returns error massage: User cannot follow him/herself

  Scenario: 4-User should not be allowed to send bad requests
    When User sends invalid follow request
    Then System returns error massage: Invalid id of user to follow: -1
