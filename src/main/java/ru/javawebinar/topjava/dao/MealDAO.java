package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAO implements Dao<Meal> {
    private static final int EXCESS_CALORIES_VALUE = 2000;
    private static final AtomicInteger countId = new AtomicInteger(0);
    private static final ConcurrentMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    static {
        meals.put(countId.get(), new Meal(countId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(countId.get(), new Meal(countId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(countId.get(), new Meal(countId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(countId.get(), new Meal(countId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(countId.get(), new Meal(countId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(countId.get(), new Meal(countId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(countId.get(), new Meal(countId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal get(int id) {
        return meals.getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void save(Meal meal) {
        meals.put(countId.getAndIncrement(), meal);
    }

    @Override
    public void update(Meal meal) {
        meals.replace(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    public static int getCalorieLimit() {
        return EXCESS_CALORIES_VALUE;
    }

    public static int getCountId() {
        return countId.get();
    }

    public static int getAndIncrementCountId() {
        return countId.getAndIncrement();
    }
}