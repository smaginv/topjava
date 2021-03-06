package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JPAMealServiceTest extends MealServiceTest {
}
