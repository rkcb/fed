package com.fed.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(
     uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})}
)
public class Club {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @NotEmpty
    private String name;

    private String street;
    private String postalCode;
    private String city;
    private String country;

    @OneToMany(mappedBy = "club")
    private List<Player> members;

    protected Club() { }

    public static Club create(String name, String street, String postalCode, String city,
                         String country){

       Club club = new Club();

       club.name = name;
       club.street = street;
       club.postalCode = postalCode;
       club.city = city;
       club.country = country;

       return club;

    }
}
