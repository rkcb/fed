package com.fed.data;


import com.fed.validators.EqualPasswords;
import com.fed.validators.Username;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@EqualPasswords
public class UsersData  {

    @Email(message = "Invalid email address")
    @Username
    String username;

    @Size(min = 2, message = "Password must be at least 2 chars")
    String password;

    @Size(min = 2, message = "Password must be at least 2 chars")
    String password2;

    String oldPassword;

    boolean enabled;

}

