package com.example.pm_a11exercicio01.persistence;

import java.sql.SQLException;

public interface IJogadorDao {
    JogadorDao open() throws SQLException;
    void close();
}
