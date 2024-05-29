package com.example.pm_a11exercicio01.controller;

import com.example.pm_a11exercicio01.model.Jogador;
import com.example.pm_a11exercicio01.persistence.JogadorDao;
import com.example.pm_a11exercicio01.persistence.TimeDao;

import java.sql.SQLException;
import java.util.List;

public class JogadorController implements IController<Jogador> {

    private final JogadorDao jDao;

    public JogadorController(JogadorDao jDao){
        this.jDao = jDao;
    }

    @Override
    public void inserir(Jogador jogador) throws SQLException {
        if (jDao.open() == null){
            jDao.open();
        }
        jDao.insert(jogador);
        jDao.close();
    }

    @Override
    public void modificar(Jogador jogador) throws SQLException {
        if (jDao.open() == null){
            jDao.open();
        }
        jDao.update(jogador);
        jDao.close();
    }

    @Override
    public void deletar(Jogador jogador) throws SQLException {
        if (jDao.open() == null){
            jDao.open();
        }
        jDao.delete(jogador);
        jDao.close();
    }

    @Override
    public Jogador buscar(Jogador jogador) throws SQLException {
        if (jDao.open() == null){
            jDao.open();
        }
        jogador = jDao.findBy(jogador);
        jDao.close();
        return jogador;
    }

    @Override
    public List<Jogador> listar() throws SQLException {
        if (jDao.open() == null){
            jDao.open();
        }
        List<Jogador> list = jDao.findAll();
        jDao.close();
        return list;
    }
}
