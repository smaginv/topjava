package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        MealsUtil.meals2.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(userId, ConcurrentHashMap::new).put(meal.getId(), meal);
            return meal;
        }

        return Objects.requireNonNull(repository.computeIfPresent(userId, (k, v) -> {
            v.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            return v;
        })).get(meal.getId());
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.getOrDefault(userId, new HashMap<>()).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.getOrDefault(userId, new HashMap<>()).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getOrDefault(userId, new HashMap<>()).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getOrDefault(userId, new HashMap<>()).values().stream()
                .filter(meal -> isBetweenHalfOpen(meal.getDate().toEpochDay(), startDate.toEpochDay(), endDate.plusDays(1).toEpochDay()))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

