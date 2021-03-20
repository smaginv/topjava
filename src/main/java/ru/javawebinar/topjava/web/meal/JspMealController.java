package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/filter")
    public String getAllWithFilter(@RequestParam String startDate, @RequestParam String endDate,
                                   @RequestParam String startTime, @RequestParam String endTime, Model model) {
        model.addAttribute("meals", super.getBetween(parseLocalDate(startDate), parseLocalTime(startTime),
                parseLocalDate(endDate), parseLocalTime(endTime)));
        return "meals";
    }

    @GetMapping("/update/{id}")
    public String get(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @GetMapping("/add")
    public String add(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @PostMapping
    public String save(@RequestParam String dateTime, @RequestParam String description, @RequestParam Integer calories,
                       @RequestParam(required = false) Integer id) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);

        if (Objects.nonNull(id)) {
            super.update(meal, id);
        } else {
            super.create(meal);
        }
        return "redirect:/meals";
    }
}