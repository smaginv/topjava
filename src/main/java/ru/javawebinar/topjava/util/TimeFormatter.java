package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String stringTime, Locale locale) throws ParseException {
        return stringTime.isEmpty() ? null : LocalTime.parse(stringTime, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return DateTimeFormatter.ISO_LOCAL_TIME.format(localTime);
    }
}
