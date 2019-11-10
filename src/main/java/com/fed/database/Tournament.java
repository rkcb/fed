package com.fed.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * Tournament keeps record of players who registered in a tournament and
 * played it
 */

@Data
@Entity
public class Tournament  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @Valid
    private CalendarEvent calendarEvent;

    @OneToOne
    private CompetitionRegistration competitionRegistration;

    protected Tournament(){}

    protected Tournament(CalendarEvent calendarEvent){
        this.calendarEvent = calendarEvent;
    }

    public static Tournament create(String title, Timestamp start){

        CalendarEvent calendarEvent = CalendarEvent.create(title, start);

        Tournament tournament = new Tournament(calendarEvent);

        return tournament;
    }

// TODO:
//    1. collect registered players as entities: to be used in tournament view
//    2. add also PBN result files
// NOTES:
//    1. all usage from users go via controllers
//    2. regular users need modification for registration only (only registration owner can modify)
//    3. admins can CRUD tournaments via controllers and upload results
//
//






}
