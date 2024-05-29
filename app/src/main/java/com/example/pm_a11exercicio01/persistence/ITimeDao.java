package com.example.pm_a11exercicio01.persistence;

import java.sql.SQLException;

public interface ITimeDao {
    TimeDao open() throws SQLException;
    void close();
}
