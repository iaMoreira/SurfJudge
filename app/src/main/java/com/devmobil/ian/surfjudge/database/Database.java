package com.devmobil.ian.surfjudge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "dados", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createChampion());
        db.execSQL(createSurfer());
        db.execSQL(createChampionSurfer());
        db.execSQL(createNote());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
            //(OR)
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    private String createChampion() {
        String sql = "CREATE TABLE CHAMPION ( " +
                " ID INTEGER, " +
                " TITLE TEXT NOT NULL, " +
                " DESCRIPTION TEXT NOT NULL, " +
                " IMAGE TEXT, " +
                " WAVES INTEGER NOT NULL, " +
                " CATEGORY TEXT NOT NULL, " +
                " DATE_TIME TEXT NOT NULL, " +
                " PLACE TEXT NOT NULL " +
                ");";

        return sql;
    }

    private String createSurfer() {
        String sql = "CREATE TABLE SURFER ( " +
                " ID INTEGER, " +
                " NAME TEXT NOT NULL, " +
                " COUNTRY TEXT NOT NULL, " +
                " DATE_BIRTH TEXT NOT NULL, " +
                " PRIMARY KEY(ID) " +
                ");";

        return sql;
    }

    private String createChampionSurfer() {
        String sql = "CREATE TABLE CHAMPION_SURFER ( " +
                " ID INTEGER, " +
                " CHAMPION_ID INTEGER NOT NULL, " +
                " SURFER_ID INTEGER NOT NULL, " +
                " COLOR TEXT NOT NULL, " +
                " PRIMARY KEY(ID), " +
                " FOREIGN KEY(SURFER_ID) REFERENCES SURFER(ID), " +
                " FOREIGN KEY(CHAMPION_ID) REFERENCES CHAMPION(ID) " +
                ");";

        return sql;
    }

    private String createNote() {
        String sql = "CREATE TABLE NOTE ( " +
                " ID INTEGER, " +
                " VALUE NUMERIC NOT NULL, " +
                " CHAMPION_SURFER_ID INTEGER NOT NULL, " +
                " WAVE INTEGER NOT NULL, " +
                " PRIMARY KEY(ID) ,"+
                " FOREIGN KEY(CHAMPION_SURFER_ID) REFERENCES NOTE(ID) "+
                ")";

        return sql;
    }


}
