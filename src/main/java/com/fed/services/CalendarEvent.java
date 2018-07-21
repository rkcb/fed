package com.fed.services;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class CalendarEvent {

    @Id
    private int id;

    private String subject;

    @Basic
    private Timestamp begin;
    @Basic
    private Timestamp start;

    public CalendarEvent(){};

}
