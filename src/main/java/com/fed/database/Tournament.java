package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

/**
 * Tournament keeps record of players who registered in a tournament and
 * played it
 */

@Data
@Entity(name="tournaments")
public class Tournament  {

    protected List<Player> registered;

    protected List<Player> played;








}
