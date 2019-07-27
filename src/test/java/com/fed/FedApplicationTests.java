package com.fed;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FedApplicationTests {

    @Autowired
    private DataSource dataSource;

    private JdbcUserDetailsManager manager;

    @Test
    public void fedTest(){
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void createUser() {

        manager = new JdbcUserDetailsManager(dataSource);

        Collection<GrantedAuthority> collection = new ArrayList<>(0);

        User user = new User("testEsco", "password123!", collection);

        manager.createUser(user);

        Assert.assertTrue(manager.userExists("testEsco"));



    }
}
