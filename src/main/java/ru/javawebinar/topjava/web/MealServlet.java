package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Dao<Meal> dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new InMemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("forward to meals");
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.EXCESS_CALORIES_VALUE);
            request.setAttribute("mealsTo", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        switch (action) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                boolean performed = dao.delete(id);
                log.debug(performed ? "meal has been deleted" : "meal was not deleted");
                response.sendRedirect("meals");
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.get(id);
                log.debug("forward to edit the meal: " + meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case "add":
                log.debug("forward to add new meal");
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));

        String description = request.getParameter("description");

        int calories = Integer.parseInt(request.getParameter("calories"));

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
