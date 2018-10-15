package com.fed.database;

import lombok.Data;

import javax.persistence.*;
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

    private String name;

    private String street;
    private String postalCode;
    private String city;
    private String country;

    @OneToMany(mappedBy = "code")
    private List<Player> members;

}
