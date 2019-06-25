package com.fed.database;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class Users extends User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    public Users(){
        super("emptyuser", "emptypassword123!", new ArrayList<>());
    }

    private Users(String username, String password,
                 Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
    }

    public static Users create(String username, String password, Collection<?
            extends GrantedAuthority> authorities){

        return new Users(username, password, authorities);
    }


}
