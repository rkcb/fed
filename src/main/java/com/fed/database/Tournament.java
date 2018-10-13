package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Tournament keeps record of players who registered in a tournament and
 * played it
 */

@Data
@Entity(name="tournaments")
public class Tournament  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    protected List<Player> registered;
//
//    protected List<Player> played;








}
