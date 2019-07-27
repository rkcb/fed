package com.fed.data;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Data
public class UsersData {

    @Email(message = "invalid email address")
    String username;
    @Min(value = 2, message = "password must be at least 2 chars")
    String password;
    String password2;
    String oldPassword;
    boolean enabled;
}


