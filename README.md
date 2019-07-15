# Basic twitter clone code challenge

## About application
This is a rest API that supports basic operations of a twitter-like messaging system:
* Users can post messages of length of up to 140 chars;
* User can see the list of messages he/she's posted in a reverse chronological order;
* User can follow another user. Following is not symmetric - user1 can follow user2 without user2 following user1. User cannot follow him/herself. Users can mutually follow each other;
* User can see the list of messages of the users that he/she follows in the reverse chronological order.

As per the requirement users registration is automatic and no security restrictions are in place. If a user does not exist in the system and he posts any request he gets registered automatically.

## API endpoints

**Method GET: /user/{userId1}/follow/{userId2}** - request for userId1 to follow userId2;
**Method GET: /user/{userId1}/unfollow/{userId2}** - request for userId1 to unfollow userId2;
**Method GET: /tweet/by/{userId}** - show tweets of a single userId;
**Method GET: /tweet/for/{userId}** - show tweets of all users followed by userId;
**Method POST: /tweet/** - add a new tweet. Object schema is listed below. Content-Type: application/json.
Object schema:
```json
{
  "$schema": "Add tweet",
  "type": "object",
  "properties": {
    "userId": {
      "type": "integer"
    },
    "text": {
      "type": "string"
    }
  },
  "required": [
    "userId",
    "text"
  ]
}
```
Example: { "userId": 1, "text": "Hello world!"}