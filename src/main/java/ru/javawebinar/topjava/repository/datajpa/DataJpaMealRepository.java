package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudUserRepository.getOne(userId));
        if (meal.isNew()) {
            return crudMealRepository.save(meal);
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudMealRepository.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserIdOrderByDateTimeDesc(startDateTime, endDateTime, userId);
    }

    public Meal getMealByIdWithUser(int id, int userId) {
        return crudMealRepository.getMealByIdWithUser(id, userId);
    }
}
