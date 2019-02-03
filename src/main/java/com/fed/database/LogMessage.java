package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class LogMessage {

    private String message;
    private String playerCode; // user who caused the message

}
