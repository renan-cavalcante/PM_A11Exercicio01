package com.example.pm_a11exercicio01.persistence;

import java.sql.SQLException;
import java.util.List;

public interface ICRUDDao<T> {

    void insert(T t) throws SQLException;
    int update(T t)throws SQLException;
    void delete(T t)throws SQLException;
    T findBy(T t)throws SQLException;
    List<T> findAll()throws SQLException;
}
