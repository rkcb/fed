package com.fed.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(
        uniqueConstraints=
                {@UniqueConstraint(columnNames={"code"}), @UniqueConstraint(columnNames={
                        "username"})}
)
public class Player {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @NotEmpty
    private String code;
    @NotEmpty
    private String username;
    private String password;

    private String firstName;
    private String lastname;

    private String street;
    private String postalCode;
    private String city;
    private String country;

    private String phone;

    @Email
    @NotEmpty
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

    public static Player create(String username, String code, String email, String password){

        Player player = new Player();

        player.username = username;
        player.code = code;
        player.email = email;
        player.password = password;

        return player;

    }



}
