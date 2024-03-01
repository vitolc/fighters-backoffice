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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy H:m");
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("The date provided is not valid. a correct format, for example, would be '23/3/2023 14:30'");
        }
    }
}
