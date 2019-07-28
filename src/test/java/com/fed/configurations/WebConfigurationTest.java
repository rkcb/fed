package com.fed.configurations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebConfigurationTest {

    @Autowired
    private JdbcUserDetailsManager manager;

    @Test
    public void jdbcUserDetailsManagerTest() {
        Assert.assertNotNull(manager);
    }


}
