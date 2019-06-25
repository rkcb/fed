package com.fed;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class FedApplicationTests {

    @Autowired
    private JdbcUserDetailsManager manager;

    @Test
    public void testManager(){
        Assert.assertNotNull(manager);
    }

    @Test
    public void testUser(){


    }

}
