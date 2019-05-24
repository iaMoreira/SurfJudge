package com.devmobil.ian.surfjudge.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.devmobil.ian.surfjudge.database.Database;
import com.devmobil.ian.surfjudge.model.Champion;

import java.util.ArrayList;

public class Champions {

    private Context context;
    private SQLiteDatabase conection;

    public Champions(Context context)
    {
        this.context = context;
                
        verifica();
    }

    private void verifica() {
        if (conection == null) {
            Database database = new Database(context);
            conection = database.getWritableDatabase();
        }
    }

    public long insert(Champion champion) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", champion.getTitle());
        contentValues.put("DESCRIPTION", champion.getDescription());
        contentValues.put("IMAGE", champion.getImage());
        contentValues.put("WAVES", champion.getWaves());
        contentValues.put("CATEGORY", champion.getCategory());
        contentValues.put("DATE_TIME", champion.getDate_time());
        contentValues.put("PLACE", champion.getPlace());

        try {
            verifica();
            return conection.insertOrThrow("CHAMPION", null, contentValues);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }

    }

    public int delete(int id) {
        String[] param = new String[1];
        param[0] = String.valueOf(id);
        try {
            verifica();
            return conection.delete("CHAMPION", "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public int update(Champion champion) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", champion.getTitle());
        contentValues.put("DESCRIPTION", champion.getDescription());
        contentValues.put("IMAGE", champion.getImage());
        contentValues.put("WAVES", champion.getWaves());
        contentValues.put("CATEGORY", champion.getCategory());
        contentValues.put("DATE_TIME", champion.getDate_time());
        contentValues.put("PLACE", champion.getPlace());
        String[] param = new String[1];
        param[0] = String.valueOf(champion.getId());
        try {
            verifica();
            return conection.update("CHAMPION", contentValues, "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public ArrayList<Champion> all() {
        ArrayList<Champion> championes = new ArrayList<>();
        try {
            verifica();
            String sql = "SELECT * FROM CHAMPION ORDER BY NOME ASC";
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                do {
                    Champion champion = new Champion();
                    champion.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                    champion.setTitle(results.getString(results.getColumnIndexOrThrow("TITLE")));
                    champion.setDescription(results.getString(results.getColumnIndexOrThrow("DESCRIPTION")));
                    champion.setImage(results.getString(results.getColumnIndexOrThrow("IMAGE")));
                    champion.setWaves(results.getInt(results.getColumnIndexOrThrow("WAVES")));
                    champion.setCategory(results.getString(results.getColumnIndexOrThrow("CATEGORY")));
                    champion.setDate_time(results.getString(results.getColumnIndexOrThrow("DATE_TIME")));
                    champion.setPlace(results.getString(results.getColumnIndexOrThrow("PLACE")));
                    championes.add(champion);

                } while (results.moveToNext());
                results.close();
                return championes;
            } else
                return championes;
        } catch (SQLException ex) {
            return championes;
        } catch (NullPointerException ex) {
            return championes;
        }
    }

    public Champion find(int id) {
        Champion champion = new Champion();
        try {
            verifica();
            String sql = "SELECT * FROM CHAMPION WHERE ID = " + String.valueOf(id) + "";
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                champion.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                champion.setTitle(results.getString(results.getColumnIndexOrThrow("TITLE")));
                champion.setDescription(results.getString(results.getColumnIndexOrThrow("DESCRIPTION")));
                champion.setImage(results.getString(results.getColumnIndexOrThrow("IMAGE")));
                champion.setWaves(results.getInt(results.getColumnIndexOrThrow("WAVES")));
                champion.setCategory(results.getString(results.getColumnIndexOrThrow("CATEGORY")));
                champion.setDate_time(results.getString(results.getColumnIndexOrThrow("DATE_TIME")));
                champion.setPlace(results.getString(results.getColumnIndexOrThrow("PLACE")));
                results.close();
                return champion;
            } else
                return champion;
        } catch (SQLException ex) {
            return champion;
        } catch (NullPointerException ex) {
            return champion;
        }
    }

}
