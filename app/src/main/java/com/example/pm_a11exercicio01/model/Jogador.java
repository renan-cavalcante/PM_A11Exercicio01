package com.example.pm_a11exercicio01.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Jogador {
    private int id;
    private String nome;
    private LocalDate dataNasc;
    private float altura;
    private float peso;
    private Time time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(" - ").append(nome);
        sb.append(", ").append(dataNasc.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sb.append(", ").append(altura);
        sb.append("m, ").append(peso);
        sb.append("Kg, time:").append(time);
        return sb.toString();
    }
}
