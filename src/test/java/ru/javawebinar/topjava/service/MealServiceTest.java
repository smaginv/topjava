package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(meal2_User.getId(), USER_ID);
        assertMatch(meal, meal2_User);
    }

    @Test
    public void delete() {
        service.delete(meal8_Admin.getId(), ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal8_Admin.getId(), ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(meal7_User.getId(), USER_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2021, Month.FEBRUARY, 19),
                LocalDate.of(2021, Month.FEBRUARY, 19), USER_ID);
        assertMatch(all, meal4_User, meal3_User, meal2_User);
    }

    @Test
    public void getAllWithNullFilter() {
        List<Meal> all = service.getBetweenInclusive(null, null, ADMIN_ID);
        assertMatch(all, meal13_Admin, meal12_Admin, meal11_Admin, meal10_Admin, meal9_Admin, meal8_Admin);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, meal7_User, meal6_User, meal5_User, meal4_User, meal3_User, meal2_User);
    }

    @Test
    public void getSomeoneMeal() {
        assertThrows(NotFoundException.class, () -> service.get(meal5_User.getId(), ADMIN_ID));
    }

    @Test
    public void deleteSomeoneMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(meal9_Admin.getId(), USER_ID));
    }

    @Test
    public void updateSomeoneMeal() {
        assertThrows(NotFoundException.class, () -> service.update(meal6_User, ADMIN_ID));
    }

    @Test
    public void getNonexistentMeal() {
        assertThrows(NotFoundException.class, () -> service.get(100, USER_ID));
    }

    @Test
    public void deleteNonexistentMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(100, ADMIN_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new Meal(meal3_User.getDateTime(), "breakfast", 500), USER_ID));
    }
}