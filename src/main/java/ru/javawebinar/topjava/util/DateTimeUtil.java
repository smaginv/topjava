package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T currentValue, T initialValue, T endValue) {
        return currentValue.compareTo(initialValue) >= 0 && currentValue.compareTo(endValue) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate toLocalDate(String ld) {
        return StringUtils.hasText(ld) ? LocalDate.parse(ld) : null;
    }

    public static LocalTime toLocalTime(String lt) {
        return StringUtils.hasText(lt) ? LocalTime.parse(lt) : null;
    }
}

