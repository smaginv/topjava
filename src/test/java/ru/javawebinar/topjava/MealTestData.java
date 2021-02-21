package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    private static int mealId = START_SEQ + 1;

    public static final Meal meal2_User = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 19, 10, 0, 0), "Завтрак", 500);
    public static final Meal meal3_User = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 19, 13, 0, 0), "Обед", 1000);
    public static final Meal meal4_User = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 19, 20, 0, 0), "Ужин", 500);
    public static final Meal meal5_User = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 20, 10, 0, 0), "Завтрак", 550);
    public static final Meal meal6_User = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 20, 13, 0, 0), "Обед", 1100);
    public static final Meal meal7_User = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 20, 20, 0, 0), "Ужин", 500);

    public static final Meal meal8_Admin = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 19, 10, 0, 0), "Завтрак", 550);
    public static final Meal meal9_Admin = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 19, 13, 0, 0), "Обед", 1100);
    public static final Meal meal10_Admin = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 19, 20, 0, 0), "Ужин", 600);
    public static final Meal meal11_Admin = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 20, 10, 0, 0), "Завтрак", 500);
    public static final Meal meal12_Admin = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 20, 13, 0, 0), "Обед", 1000);
    public static final Meal meal13_Admin = new Meal(++mealId, LocalDateTime.of(2021, Month.FEBRUARY, 20, 20, 0, 0), "Ужин", 500);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.FEBRUARY, 19, 11, 0, 0), "Завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal7_User);
        updated.setDateTime(LocalDateTime.of(2021, Month.FEBRUARY, 19, 21, 21, 21));
        updated.setDescription("dinner");
        updated.setCalories(555);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
