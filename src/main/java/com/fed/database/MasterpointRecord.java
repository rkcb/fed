package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "masterpoints")
public class MasterpointRecord {

//    @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (player_code) REFERENCES player (code)")
    private String code;

    public MasterpointRecord(String code) {this.code = code;}
}
