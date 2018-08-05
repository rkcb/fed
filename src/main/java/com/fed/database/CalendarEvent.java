package com.fed.database;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
public class CalendarEvent {

    @Id
    private int id;

    @NotNull
    private String subject;

    private String description;

    @NotNull
    private String location;

    @NotNull
    @Basic
    private Timestamp begin;

    @Basic
    private Timestamp end;

    public static CalendarEvent create(Timestamp begin, Timestamp end, String subject, String
            description, String location){

        CalendarEvent calendarEvent = new CalendarEvent();

        calendarEvent.begin = begin;
        calendarEvent.end = end;
        calendarEvent.subject = subject;
        calendarEvent.description = description;
        calendarEvent.location = location;

        return calendarEvent;
    }

}
