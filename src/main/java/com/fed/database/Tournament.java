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

    protected Tournament(){}

    protected Tournament(CalendarEvent calendarEvent){
        this.calendarEvent = calendarEvent;
    }

    public static Tournament create(String title, Timestamp start){

        CalendarEvent calendarEvent = CalendarEvent.create(title, start);

        Tournament tournament = new Tournament(calendarEvent);

        return tournament;
    }
//    protected List<Player> registered;
//
//    protected List<Player> played;








}
