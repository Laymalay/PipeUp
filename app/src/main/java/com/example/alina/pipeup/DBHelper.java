package com.example.alina.pipeup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by alina on 11/20/17.
 */

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DATABASE", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table users ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "surname text,"
                + "imagelink text,"
                + "email text" + ");");
        db.execSQL("create table messages ("
                + "id integer primary key autoincrement,"
                + "sender text,"
                + "receiver text,"
                + "date text,"
                + "txt text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
