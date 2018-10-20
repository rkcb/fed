package com.fed.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Calendar;

@Data
@Entity
public class MasterpointRecord {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotNull
    @Min(0)
    private Float masterpoints;

    @NotEmpty
    private String explanation;

    @NotNull
    private Timestamp earned;

    @NotNull
    private Timestamp added;


    @NotNull
    //TODO: add a foreign key constraint
    private String adderCode;

    @NotNull
    //TODO: add a foreign key constraint
    private String recipientCode;

    protected MasterpointRecord(){}

    public static MasterpointRecord create(Float masterpoints, String explanation,
                                           String adderCode, String recipientCode, Timestamp earned){
        MasterpointRecord masterpointRecord = new MasterpointRecord();

        masterpointRecord.masterpoints = masterpoints;
        masterpointRecord.explanation = explanation;
        masterpointRecord.adderCode = adderCode;
        masterpointRecord.recipientCode = recipientCode;
        masterpointRecord.earned = earned;

        Calendar calendar = Calendar.getInstance();
        masterpointRecord.added = new Timestamp(calendar.getTimeInMillis());

        return masterpointRecord;
    }

}
