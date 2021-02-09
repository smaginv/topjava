package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealDAOInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Dao<Meal> dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new MealDAOInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("redirect to meals");
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getExcessCaloriesValue());
            request.setAttribute("mealsTo", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        switch (Objects.requireNonNull(action)) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                boolean performed = dao.delete(id);
                log.debug(performed ? "meal has been deleted" : "meal was not deleted");
                response.sendRedirect("meals");
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.get(id);
                log.debug("redirect to edit meals: " + meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case "add":
                log.debug("redirect to add meals");
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String dateTimeString = request.getParameter("dateTime");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, TimeUtil.getDateTimeFormatter());

        String description = request.getParameter("description");

        String caloriesString = request.getParameter("calories");
        int calories = Integer.parseInt(caloriesString);

        String idString = request.getParameter("id");
        if (idString == null || idString.isEmpty()) {
            Meal meal = dao.save(new Meal(dateTime, description, calories));
            log.debug("added new meal: " + meal);
        } else {
            int id = Integer.parseInt(idString);
            Meal meal = dao.save(new Meal(id, dateTime, description, calories));
            log.debug("meal changed: " + meal);
        }

        response.sendRedirect("meals");
    }
}
