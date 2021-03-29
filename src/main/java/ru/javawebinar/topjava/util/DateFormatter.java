package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String stringDate, Locale locale) throws ParseException {
        return stringDate.isEmpty() ? null : LocalDate.parse(stringDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
    }
}
