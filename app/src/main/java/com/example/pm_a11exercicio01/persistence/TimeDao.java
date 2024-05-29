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
import java.util.ArrayList;
import java.util.List;

public class TimeDao implements ITimeDao, ICRUDDao<Time> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public TimeDao(Context context){
        this.context = context;
    }

    @Override
    public void insert(Time time) throws SQLException {
        ContentValues contentValues = getTime(time);
        db.insert("time",null,contentValues);
    }

    @Override
    public int update(Time time) throws SQLException {
        ContentValues contentValues = getTime(time);
        return db.update("time",contentValues,"codigo ="+ time.getCodigo(), null);

    }

    @Override
    public void delete(Time time) throws SQLException {
        db.delete("time", "codigo = "+ time.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Time findBy(Time time) throws SQLException {
        String sql = "SELECT * FROM time WHERE codigo = "+time.getCodigo();
        Cursor cursor = db.rawQuery(sql, null);
        Time t = new Time();
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){

            t.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            t.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            t.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

        }
        cursor.close();
        return t;
    }

    @SuppressLint("Range")
    @Override
    public List<Time> findAll() throws SQLException {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT *  FROM time";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            times.add(time);
            cursor.moveToNext();
        }
        cursor.close();
        return times;
    }

    @Override
    public TimeDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();;
    }

    private ContentValues getTime(Time time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", time.getCodigo());
        contentValues.put("nome", time.getNome());
        contentValues.put("cidade", time.getCidade());

        return contentValues;
    }
}
