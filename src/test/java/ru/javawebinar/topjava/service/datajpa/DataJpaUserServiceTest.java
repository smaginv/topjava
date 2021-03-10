package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        MEAL_MATCHER.assertMatch(user.getMeals(), meals);
    }

    @Test
    public void updateWithCascadeAll() {
        User updated = getUpdated(); //USER_ID
        MealTestData.meal1.setUser(admin);  //set user=admin to user's meal
        updated.setMeals(List.of(MealTestData.meal1));
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    public void userHasNoFood() {
        User newUser = service.create(getNew());
        User returnedUser = service.getWithMeals(newUser.id());
        USER_MATCHER.assertMatch(returnedUser, newUser);
        MEAL_MATCHER.assertMatch(returnedUser.getMeals(), Collections.emptyList());
    }
}
