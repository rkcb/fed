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
public class NewUserData {

    @Autowired
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private JdbcUserDetailsManager manager;

    @Email
    String username;

    @Size(min = 2)
    String password;

    @Size(min = 2)
    String password2;

    @AssertTrue(message = "passwords are not equal")
    public boolean validPasswords(){
        return password != null && password2 != null &&
                password.equals(password2);
    }

    @AssertTrue(message = "username is already in use")
    public boolean validUsername(){
        return !manager.userExists(username);
    }
}
