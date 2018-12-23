package com.fed.converters;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

public class TimestampStringToTimestamp implements Converter<String, Timestamp> {

    @Override
    public Timestamp convert(String isoDateTime) {

        try {
            Calendar calendar = Calendar.getInstance();
            return new Timestamp(calendar.getTimeInMillis());
        } catch (DateTimeParseException exception){
            throw new IllegalArgumentException(exception.getMessage());
        }

    }

}
