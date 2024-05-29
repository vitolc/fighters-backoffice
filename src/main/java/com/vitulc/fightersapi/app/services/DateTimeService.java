package com.vitulc.fightersapi.app.services;

import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class DateTimeService {

    static public LocalDateTime parseDate(String date) {
        if (date == null) return null;

        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("The date provided is not valid. a correct format, for example, would be '2023-03-23T14:30:00Z'");
        }
    }
}
