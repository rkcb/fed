package com.fed.database;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * keeps registration status for a tournament
 * whichkeep registration status for a tournament which
 * can be individual, pair or team type
 */

@Data
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"player1Code" , "player2Code",
        "player3Code" , "player4Code", "player5Code" , "player6Code"})})
public class CompetitionRegistration {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @OneToOne(mappedBy = "competitionRegistration")
    @NotNull
    Tournament tournament;

    private int maxPlayers;
    private int minPlayers;

    @Size(max = 20)
    String player1Code;
    @Size(max = 20)
    String player2Code;
    @Size(max = 20)
    String player3Code;
    @Size(max = 20)
    String player4Code;
    @Size(max = 20)
    String player5Code;
    @Size(max = 20)
    String player6Code;

}
