package com.fed.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class UsersRestController {

    @Autowired
    DataSource dataSource;

    private JdbcUserDetailsManager manager;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @PostMapping("/users/updatepassword")
    String updateUserPassword(@RequestParam(name = "username") String username) {

        /*
        if (!usersRepository.existsById(username)){
            return "no such username";
        }

        if (password != null && password == password2) {

            Users user = usersRepository.getOne(username);
            user.setPassword(encoder.encode(password));
            manager = new JdbcUserDetailsManager(dataSource);
            manager.updateUser(user);

            return "updated successfully";
        } else {
            return "invalid passwords";
        }

         */
//        return "username: " + username + " password: "  + password;



        return "username value: " + username;
    }


}
