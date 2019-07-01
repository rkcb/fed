package com.fed.configurations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebConfigurationTest {


//    @TestConfiguration
//    static class WebConf {
//
//
//
//
//    }


    @Autowired
    private DataSource datasource;

    @Test
    public void jdbcUserDetailsManagerTest(){

        Assert.assertTrue(true);

    }


}
