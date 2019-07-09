package com.fed.restcontrollers;

import com.fed.data.Roles;
import com.fed.data.UsersData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Collection;

@RestController
public class UsersRestController {

    final int passwordLenth = 1;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcUserDetailsManager manager;

    @PostMapping("/create")
    String createUsers(@RequestBody UsersData data){

        data.setPassword("password");
        data.setPassword2("password");
        data.setUsername("escobar");
        boolean exists = manager.userExists(data.getUsername());
        boolean passwordNotNull = data.getPassword() != null;
        boolean passwordsMatch = data.getPassword().equals(data.getPassword2());
        boolean passwordLengthOk = passwordNotNull && data.getPassword().length() >= passwordLenth;

//        if (dataSource == null){
//            return " dataSource is null";
//        }


        if (exists){
            return "Username exists already";
        }

        if (!passwordNotNull){
            return "Invalid password length";
        }

        if (!passwordsMatch){
            return "Passwords do not match";
        }

        if (!passwordLengthOk){
            return "Password is too short; minimum length is " + passwordLenth;
        }

        Collection<GrantedAuthority> authorities = Roles.of(Roles.Value.PLAYER);
//
        User user = new User(data.getUsername(), data.getPassword(), authorities);
//
        manager.createUser(user);

        return "User created";
    }

    @PostMapping("/users/update/password")
    String updateUserPassword(@RequestBody UsersData data) {

//        if (!manager.userExists(data.getUsername())){
//            return "no such username found";
//        }

        if (data.getPassword() != null && data.getPassword().length() >= passwordLenth ) {

            return "updated successfully";
        } else {
            return "too short password; required at least " + passwordLenth;
        }

    }



}
