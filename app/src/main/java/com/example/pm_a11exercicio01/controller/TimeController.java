package com.example.pm_a11exercicio01.controller;

import com.example.pm_a11exercicio01.model.Time;
import com.example.pm_a11exercicio01.persistence.TimeDao;

import java.sql.SQLException;
import java.util.List;

public class TimeController implements  IController<Time> {

    private final TimeDao tDao;

    public TimeController(TimeDao tDao){
        this.tDao = tDao;
    }

    @Override
    public void inserir(Time time) throws SQLException {
        if (tDao.open() == null){
            tDao.open();
        }
        tDao.insert(time);
        tDao.close();
    }

    @Override
    public void modificar(Time time) throws SQLException {
        if (tDao.open() == null){
            tDao.open();
        }
        tDao.update(time);
        tDao.close();
    }

    @Override
    public void deletar(Time time) throws SQLException {
        if (tDao.open() == null){
            tDao.open();
        }
        tDao.delete(time);
        tDao.close();
    }

    @Override
    public Time buscar(Time time) throws SQLException {
        if (tDao.open() == null){
            tDao.open();
        }
        time = tDao.findBy(time);
        tDao.close();
        return time;
    }

    @Override
    public List<Time> listar() throws SQLException {
        if (tDao.open() == null){
            tDao.open();
        }
        List<Time> list = tDao.findAll();
        tDao.close();
        return list;
    }
}
