package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Club {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    private String name;

    private String street;
    private String postalCode;
    private String city;
    private String country;

//    @OneToMany(mappedBy = "code")
//    private List<Player> members;

}
