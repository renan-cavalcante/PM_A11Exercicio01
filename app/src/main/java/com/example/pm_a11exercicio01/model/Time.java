package com.example.pm_a11exercicio01.model;

public class Time {
    private int codigo;
    private String nome;
    private String cidade;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(codigo);
        sb.append(" - ").append(nome);
        sb.append(", ").append(cidade);

        return sb.toString();
    }
}
