package com.example.pm_a11exercicio01.controller;

import java.sql.SQLException;
import java.util.List;

public interface IController<T> {
    void inserir(T t) throws SQLException;
    void modificar(T t) throws SQLException;
    void deletar(T t) throws SQLException;
    T buscar(T t) throws SQLException;
    List<T> listar() throws SQLException;


}
