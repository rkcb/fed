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

    @NotNull
    private String subject;

    private String description;

    private String location;

    @Setter(AccessLevel.NONE)
    @NotNull
    @Basic
    private LocalDateTime begin;

    @Setter(AccessLevel.NONE)
    @Basic
    private LocalDateTime end;

    /**
     * @param begin e.g. 2018-11-15T08:22:12
     * @param end
     * @param subject
     * @param description
     * @param location
     * @return
     */
    public static CalendarEvent create(String begin, String end, String subject, String
            description, String location){

        CalendarEvent calendarEvent = new CalendarEvent();

        calendarEvent.begin = LocalDateTime.parse(begin);
        calendarEvent.end = LocalDateTime.parse(end);
        calendarEvent.subject = subject;
        calendarEvent.description = description;
        calendarEvent.location = location;

        return calendarEvent;
    }

    public void setBegin(String begin) {
        this.begin = LocalDateTime.parse(begin);
    }

    public void setEnd(String end) {
        this.end = LocalDateTime.parse(end);
    }
}
