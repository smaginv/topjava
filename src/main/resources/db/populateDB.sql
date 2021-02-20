DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, description, calories, date_time)
VALUES (100000, 'Завтрак', 500, '2021-02-19 10:00:00'),
       (100000, 'Обед', 1000, '2021-02-19 13:00:00'),
       (100000, 'Ужин', 500, '2021-02-19 20:00:00');

INSERT INTO meals (user_id, description, calories, date_time)
VALUES (100001, 'Завтрак', 500, '2021-02-19 10:00:00'),
       (100001, 'Обед', 1000, '2021-02-19 13:00:00'),
       (100001, 'Ужин', 500, '2021-02-19 20:00:00');