package com.fed.database;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
public class CalendarEvent {

    @Id
    private int id;

    private String title;

    private String description;

    private String location;

    @Setter(AccessLevel.NONE)
    @NotNull
    @Basic
    private LocalDateTime start;

    @Setter(AccessLevel.NONE)
    @Basic
    private LocalDateTime end;

    /**
     * @param start e.g. 2018-11-15T08:22:12
     * @param end
     * @param title
     * @param description
     * @param location
     * @return
     */
    public static CalendarEvent create(String start, String end, String title, String
            description, String location){

        CalendarEvent calendarEvent = new CalendarEvent();

        calendarEvent.start = LocalDateTime.parse(start);
        calendarEvent.end = LocalDateTime.parse(end);
        calendarEvent.title = title;
        calendarEvent.description = description;
        calendarEvent.location = location;

        return calendarEvent;
    }

    public void setBegin(String start) {
        this.start = LocalDateTime.parse(start);
    }

    public void setEnd(String end) {
        this.end = LocalDateTime.parse(end);
    }
}
