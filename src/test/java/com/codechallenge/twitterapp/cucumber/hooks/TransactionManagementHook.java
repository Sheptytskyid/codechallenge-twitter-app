package com.codechallenge.twitterapp.cucumber.hooks;

import com.codechallenge.twitterapp.SpringIntegrationTest;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionManagementHook extends SpringIntegrationTest {

    @Autowired
    private PlatformTransactionManager sessionFactory;
    private TransactionStatus transactionStatus;

    @Before(order = 0)
    public void setUp() {
        transactionStatus = sessionFactory.getTransaction(new DefaultTransactionDefinition());
    }

    @After
    public void tearDown() {
        sessionFactory.rollback(this.transactionStatus);
    }
}
