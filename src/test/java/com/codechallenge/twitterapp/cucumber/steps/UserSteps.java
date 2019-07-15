package com.codechallenge.twitterapp.cucumber.steps;

import com.codechallenge.twitterapp.controller.UserController;
import com.codechallenge.twitterapp.cucumber.utils.CucumberContext;
import com.codechallenge.twitterapp.dao.UserDao;
import com.codechallenge.twitterapp.model.User;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static com.codechallenge.twitterapp.cucumber.utils.CucumberContext.REST_RESPONSE_ENTITY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class UserSteps {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserController userController;
    @Autowired
    private CucumberContext context;
    private MockMvc userMvc;
    private long testUserIds = 0;

    @Before
    public void setup() {
        userMvc = standaloneSetup(userController).build();
    }

    @Given("(.+) exist(?:s|) in the system")
    public void usersExistInTheSystem(String userName) {
        if ("No user".equals(userName)) {
            userDao.deleteAll();
        } else {
            User userObject = new User(getNextUserId(), userName, new HashSet<>());
            userDao.save(userObject);
            context.addEntity(userName, userObject);
        }
    }

    @Given("(.+) is following (.+)")
    public void userIsFollowingUser(String user1, String user2) {
        User primary = getUser(user1);
        User followed = getUser(user2);
        primary.getFollowingIds().add(followed.getId());
        userDao.save(primary);
    }

    @When("(.+) sends (follow|unfollow) request to (.+)")
    public void userSendsFollowRequestToUser(String userName1, String request, String userName2) throws Exception {
        User user1 = getUser(userName1);
        User user2 = getUser(userName2);
        String url = "/user/" + user1.getId() + "/" + request + "/" + user2.getId();
        MvcResult mvcResult = userMvc.perform(get(url)).andReturn();
        context.addEntity(REST_RESPONSE_ENTITY, mvcResult);
    }

    @When("User sends invalid follow request")
    public void userSendsEmptyFollowRequest() throws Exception {
        MvcResult mvcResult = userMvc.perform(get("/user/1/follow/-1")).andReturn();
        context.addEntity(REST_RESPONSE_ENTITY, mvcResult);
    }

    @Then("(.+) gets registered in the system")
    public void userGetsRegisteredInTheSystem(String userName) {
        assertTrue(userDao.findByName(userName).isPresent());
    }

    @Then("(.+) (is|is not) in the followers list of (.+)")
    public void userIsInTheFollowersList(String user1, String isOrNot, String user2) {
        User primary = getUser(user2);
        User followed = getUser(user1);
        if ("is".equals(isOrNot)) {
            assertTrue(primary.getFollowingIds().contains(followed.getId()));
        } else {
            assertFalse(primary.getFollowingIds().contains(followed.getId()));
        }
    }

    @Then("System returns error massage: (.*)")
    public void systemReturnsErrorMassageUserCannotFollowHimHerself(String expectedMessage) throws Exception {
        MvcResult mvcResult = context.getEntity(REST_RESPONSE_ENTITY);
        String actualMessage = mvcResult.getResponse().getErrorMessage();
        assertThat("Error message returned by server is different than expected", actualMessage, is(expectedMessage));
    }

    private long getNextUserId() {
        return testUserIds++;
    }

    private User getUser(String userName) {
        return userDao.findByName(userName).orElseThrow();
    }
}
