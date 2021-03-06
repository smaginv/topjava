package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(DATAJPA)
public class DataJPAMealServiceTest extends MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void getMealByIdWithUser() {
        Meal meal = mealService.getMealByIdWithUser(MEAL1_ID, USER_ID);
        User user = meal.getUser();
        MEAL_MATCHER.assertMatch(meal, meal1);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }
}
