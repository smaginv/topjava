##curl MEALS REST API

1. Get all meals:

| HTTP method | Path         | Mapped to    |
|:----------- | ------------ | --- |
| GET         | /rest/meals |  MealRestController#getAll()   |
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/'
```

---

2. Get meal by id:

| HTTP method | Path             |  Mapped to   |
|:----------- | ---------------- | --- |
| GET         | /rest/meals/{id} |  MealRestController#get(int)   |
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100007'
```

---

3. Delete meal by id:

| HTTP method | Path             |   Mapped to  |
|:----------- | ---------------- | --- |
| DELETE      | /rest/meals/{id} |  MealRestController#delete(int)   |
```
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100007'
```

---

4. Create meal:

| HTTP method | Path         |  Mapped to   |
|:----------- | ------------ | --- |
| POST        | /rest/meals |  MealRestController#createWithLocation(Meal)   |
```
curl --location --request POST 'http://localhost:8080/topjava/rest/meals' --header 'Content-Type: application/json' --data-raw '{"id":"null", "dateTime":"2021-03-29T19:19:19", "description":"Ужин", "calories":"777"}'
```

---

5. Update meal by id:

| HTTP method | Path         |  Mapped to   |
|:----------- | ------------ | --- |
| PUT        | /rest/meals/{id} |   MealRestController#update(Meal, int)  |
```
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100003' --header 'Content-Type: application/json' --data-raw '{"id":"100003", "dateTime":"2020-01-30T13:15:19", "description":"Обед", "calories":"555"}'
```

---
6. Get meals in between:

| HTTP method | Path         |  Mapped to   |
|:----------- | ------------ | --- |
| GET        | /rest/meals/filter |  MealRestController#getBetween(LocalDate, LocalTime, LocalDate, LocalTime)   |
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=10:00&endTime=21:00'
```