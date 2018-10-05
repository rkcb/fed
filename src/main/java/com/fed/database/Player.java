package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Player {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    private String code;
    private String username;

    private String firstName;
    private String lastname;

    private String street;
    private String postalCode;
    private String city;
    private String country;

    private String phone;
    private String email;
    private Boolean alive;

    private Boolean publicEmail;
    private Boolean publicPhone;
    private Boolean publicAddress;


}
