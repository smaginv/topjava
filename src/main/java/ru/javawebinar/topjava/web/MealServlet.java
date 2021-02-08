package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private final Dao<Meal> dao = new MealDAO();
    private static final Logger log = getLogger(MealServlet.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String idString = request.getParameter("id");
        String action = request.getParameter("action");
        if (idString != null && action != null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(idString);
            dao.delete(id);
            response.sendRedirect("meals");
        } else if (idString != null && action != null && action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(idString);
            Meal meal = dao.get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
        } else if (action != null && action.equalsIgnoreCase("add")) {
            request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
        } else {
            forwardToMeals(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("adding or editing meal");

        String dateTimeString = request.getParameter("dateTime");
        LocalDateTime dateTime;
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            dateTime = LocalDateTime.now();
        } else {
            dateTime = LocalDateTime.parse(dateTimeString, formatter);
        }

        String description = request.getParameter("description");

        String caloriesString = request.getParameter("calories");
        int calories;
        try {
            calories = Integer.parseInt(caloriesString);
        } catch (NumberFormatException e) {
            calories = 0;
        }

        String idString = request.getParameter("id");
        if (idString == null || idString.isEmpty()) {
            dao.save(new Meal(MealDAO.getCountId(), dateTime, description, calories));
        } else {
            int id = Integer.parseInt(idString);
            dao.update(new Meal(dao.get(id).getId(), dateTime, description, calories));
        }

        forwardToMeals(request, response);
    }

    private void forwardToMeals (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealDAO.getCalorieLimit());
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
