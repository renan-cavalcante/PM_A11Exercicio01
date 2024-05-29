package com.example.pm_a11exercicio01.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pm_a11exercicio01.model.Jogador;
import com.example.pm_a11exercicio01.model.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao implements  IJogadorDao, ICRUDDao<Jogador>{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public JogadorDao(Context context){
        this.context = context;
    }
    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues contentValues = getJogador(jogador);
        db.insert("jogador",null,contentValues);
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues contentValues = getJogador(jogador);
        return db.update("jogador",contentValues, "id = "+ jogador.getId(), null);
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        db.delete("jogador", "id = "+ jogador.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findBy(Jogador jogador) throws SQLException {
        String sql = "SELECT j.*, t.nome as tnome, t.codigo, t.cidade FROM jogador as j join time as t on j.codigo_time = t.codigo WHERE id = "+jogador.getId();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            Time time = new Time();

            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("tnome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            jogador.setId( cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_nascimento"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);
        }
        cursor.close();
        return jogador;
    }

    @SuppressLint("Range")
    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT j.*, t.nome as tnome, t.codigo, t.cidade FROM jogador as j join time as t on j.codigo_time = t.codigo";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Time time = new Time();

            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("tnome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            Jogador jogador = new Jogador();
            jogador.setId( cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setDataNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_nascimento"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setTime(time);

            jogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadores;
    }

    @Override
    public JogadorDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();;
    }
    private static ContentValues getJogador(Jogador jogador) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", jogador.getId());
        contentValues.put("nome", jogador.getNome());
        contentValues.put("data_nascimento", jogador.getDataNasc().toString());
        contentValues.put("peso", jogador.getPeso());
        contentValues.put("altura", jogador.getAltura());
        contentValues.put("codigo_time", jogador.getTime().getCodigo());
        return contentValues;
    }
}
