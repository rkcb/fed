package com.fed.data;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UsersData {

    @Autowired
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private JdbcUserDetailsManager manager;

    @Email(message = "Invalid email address")
    String username;

    @Size(min = 2, message = "Password must be at least 2 chars")
    String password;

    @Size(min = 2, message = "Password must be at least 2 chars")
    String password2;

    String oldPassword;

    boolean enabled;

    @AssertTrue(message = "Passwords are not equal")
    public boolean validPasswords(){
        return password != null && password2 != null &&
                password.equals(password2);
    }

    @AssertTrue(message = "Username is not available")
    public boolean validUsername(){
        return !manager.userExists(username);
    }
}


