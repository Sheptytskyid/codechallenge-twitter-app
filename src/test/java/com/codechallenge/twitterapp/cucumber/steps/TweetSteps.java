package com.codechallenge.twitterapp.cucumber.steps;

import com.codechallenge.twitterapp.controller.TweetController;
import com.codechallenge.twitterapp.cucumber.utils.CucumberContext;
import com.codechallenge.twitterapp.dao.TweetDao;
import com.codechallenge.twitterapp.dto.TweetDto;
import com.codechallenge.twitterapp.model.Tweet;
import com.codechallenge.twitterapp.model.User;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.codechallenge.twitterapp.cucumber.utils.CucumberContext.REST_RESPONSE_ENTITY;
import static com.codechallenge.twitterapp.cucumber.utils.JsonConverter.fromJson;
import static com.codechallenge.twitterapp.cucumber.utils.JsonConverter.toJson;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class TweetSteps {

    @Autowired
    private TweetDao tweetDao;
    @Autowired
    private TweetController tweetController;
    @Autowired
    private CucumberContext context;
    private MockMvc tweetMvc;

    @Before
    public void setup() {
        tweetMvc = standaloneSetup(tweetController).build();
    }

    @When("(.+) posts a tweet of (\\d+) chars")
    public void userPostsTweetOfLength(String userName, int messageLength) throws Exception {
        char[] chars = new char[messageLength];
        Arrays.fill(chars, 'a');
        String message = new String(chars);
        userPostsTweet(userName, message);
    }

    @When("(.+) posts the tweet: (.+)")
    public void userPostsTweet(String userName, String message) throws Exception {
        User user = context.getEntity(userName);
        TweetDto dto = new TweetDto(user.getId(), message);
        MvcResult mvcResult = tweetMvc.perform(post("/tweet/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto))).andReturn();
        context.addEntity(REST_RESPONSE_ENTITY, mvcResult);
    }

    @When("(.+) views his tweets")
    public void userViewsHisTweets(String userName) throws Exception {
        User user = context.getEntity(userName);
        MvcResult mvcResult = tweetMvc.perform(get("/tweet/by/" + user.getId())).andReturn();
        context.addEntity(REST_RESPONSE_ENTITY, mvcResult);
    }

    @When("(.+) views tweets of the users that he follows")
    public void userViewsTweetsOfThoseHeFollows(String userName) throws Exception {
        User user = context.getEntity(userName);
        MvcResult mvcResult = tweetMvc.perform(get("/tweet/for/" + user.getId())).andReturn();
        context.addEntity(REST_RESPONSE_ENTITY, mvcResult);
    }

    @Then("(\\d+) tweet(?:s|) (?:is|are) saved in the system")
    public void tweetsAreSavedInTheSystem(int expectedNumberOfMessages) {
        int actualnumberOfMessages = tweetDao.findAll().size();
        assertThat("Number of messages in system mismatch", actualnumberOfMessages, is(expectedNumberOfMessages));
    }

    @Then("(\\d+) tweet(?:s|) (?:is|are) displayed")
    public void tweetsAreDisplayed(int expectedNumberOfMessages) throws IOException {
        Tweet[] tweets = getTweetsFromResponse();
        assertThat("Number of messages in system mismatch", tweets.length, is(expectedNumberOfMessages));
    }

    @Then("Tweets appear in reverse chronological order")
    public void messagesAreDisplayedInOrder() throws IOException {
        Tweet[] tweets = getTweetsFromResponse();
        List<Tweet> actual = List.of(tweets);
        List<Tweet> expected = Arrays.stream(tweets)
                .sorted(Comparator.comparing(Tweet::getTimeStamp).reversed())
                .collect(Collectors.toList());
        assertThat("Order of tweets mismatch", expected, is(actual));
    }

    private Tweet[] getTweetsFromResponse() throws IOException {
        MvcResult mvcResult = context.getEntity(REST_RESPONSE_ENTITY);
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        return fromJson(jsonResponse, Tweet[].class);
    }

}
