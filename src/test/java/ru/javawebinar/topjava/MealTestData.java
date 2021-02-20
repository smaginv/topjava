package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal meal2 = new Meal(100002, LocalDateTime.of(2021, Month.FEBRUARY, 19, 10, 0, 0), "Завтрак", 500);
    public static final Meal meal3 = new Meal(100003, LocalDateTime.of(2021, Month.FEBRUARY, 19, 13, 0, 0), "Обед", 1000);
    public static final Meal meal4 = new Meal(100004, LocalDateTime.of(2021, Month.FEBRUARY, 19, 20, 0, 0), "Ужин", 500);
    public static final Meal meal5 = new Meal(100005, LocalDateTime.of(2021, Month.FEBRUARY, 19, 10, 0, 0), "Завтрак", 500);
    public static final Meal meal6 = new Meal(100006, LocalDateTime.of(2021, Month.FEBRUARY, 19, 13, 0, 0), "Обед", 1000);
    public static final Meal meal7 = new Meal(100007, LocalDateTime.of(2021, Month.FEBRUARY, 19, 20, 0, 0), "Ужин", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.FEBRUARY, 19, 11, 0, 0), "Завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal7);
        updated.setDateTime(LocalDateTime.of(2021, Month.FEBRUARY, 19, 21, 21, 21));
        updated.setDescription("dinner");
        updated.setCalories(555);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        System.out.println();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
