package com.fed.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String location;

    private boolean masterpoints;

    @NotNull
    private Timestamp start;

    protected CalendarEvent(){}

    public static CalendarEvent create(String title, Timestamp start){


        CalendarEvent calendarEvent = new CalendarEvent();

        calendarEvent.title = title;
        calendarEvent.start = start;

        return calendarEvent;

    }

}
