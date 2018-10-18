package com.fed.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
public class MasterpointRecord {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotEmpty
    private Float masterpoints;

    @NotEmpty
    private String explanation;

    @NotNull
    private Timestamp earned;

    @NotNull
    //TODO: add foreign key constraint
    private Player  adderCode;

    @NotNull
    //TODO: add foreign key constraint
    private String recipientCode;

    protected MasterpointRecord(){}

}
