package com.fed.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Component
public class IsoStringToLocalDateTime implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String isoDateTime) {

        try {
            return LocalDateTime.parse(isoDateTime);
        } catch (DateTimeParseException exception){
            throw new IllegalArgumentException(exception.getMessage());
        }

    }
}
