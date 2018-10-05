package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
public class CalendarEvent  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String location;

    private boolean masterpoints;

    @NotNull
    private Timestamp start;

    public CalendarEvent(){}


}
