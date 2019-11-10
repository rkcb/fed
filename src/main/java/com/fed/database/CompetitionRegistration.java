package com.fed.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * keep registration status for a tournament which
 * can be individual, pair or team type
 */

@Data
@Entity
public class CompetitionRegistration {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @NotNull
    @OneToOne(mappedBy = "competitionRegistration")
    Tournament tournament;

    private int maxPlayers;
    private int minPlayers;

    @ElementCollection
    @Size(max = 6)
    List<Player> players;

}
