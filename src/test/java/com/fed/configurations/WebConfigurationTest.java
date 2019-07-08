package com.fed.configurations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
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

    private JdbcUserDetailsManager manager;

    @Test
    public void jdbcUserDetailsManagerTest(){


    }


}
