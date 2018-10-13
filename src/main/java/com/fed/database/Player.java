package com.fed.database;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//@Table(
//        name="players",
//        uniqueConstraints=
//        @UniqueConstraint(columnNames={"code"})
//)
public class Player {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    private String code;
    private String username;
    private String password;

    private String firstName;
    private String lastname;

    private String street;
    private String postalCode;
    private String city;
    private String country;

    private String phone;
    private String email;
    private Boolean alive;
    private Boolean leagueMember;

    private Boolean publicEmail;
    private Boolean publicPhone;
    private Boolean publicAddress;

    // TODO: add a role for player

//    @ManyToOne
//    private Club club;

    protected Player(){}

}
