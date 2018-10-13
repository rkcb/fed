package com.fed.database;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class MasterpointRecord {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

//    @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (player_code) REFERENCES player (code)")
    private String code;

    protected MasterpointRecord(){}

    public MasterpointRecord(String code) {this.code = code;}
}
