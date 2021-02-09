package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    List<T> getAll();
    T save(T t);
    boolean delete(int id);
}
