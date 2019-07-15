package com.codechallenge.twitterapp.cucumber.hooks;

import cucumber.api.java.AfterStep;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class SessionHook {

    @Autowired
    private EntityManager entityManager;

    @AfterStep
    public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
