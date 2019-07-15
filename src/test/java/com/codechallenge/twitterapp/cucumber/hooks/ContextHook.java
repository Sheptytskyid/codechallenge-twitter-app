package com.codechallenge.twitterapp.cucumber.hooks;

import com.codechallenge.twitterapp.cucumber.utils.CucumberContext;
import cucumber.api.java.After;
import org.springframework.beans.factory.annotation.Autowired;

public class ContextHook {

    @Autowired
    private CucumberContext context;

    @After
    public void tesrDown() {
        context.clear();
    }
}
