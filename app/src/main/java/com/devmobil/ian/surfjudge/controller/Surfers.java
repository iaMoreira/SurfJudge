package com.devmobil.ian.surfjudge.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.devmobil.ian.surfjudge.database.Database;
import com.devmobil.ian.surfjudge.model.Surfer;

import java.util.ArrayList;

public class Surfers {

    private Context context;
    private SQLiteDatabase conection;

    public Surfers(Context context)
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

    public long insert(Surfer surfer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", surfer.getName());
        contentValues.put("COUNTRY", surfer.getCountry());
        contentValues.put("DATE_BIRTH", surfer.getDate_birth());

        try {
            verifica();
            return conection.insertOrThrow("SURFER", null, contentValues);
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
            return conection.delete("SURFER", "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public int update(Surfer surfer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", surfer.getName());
        contentValues.put("COUNTRY", surfer.getCountry());
        contentValues.put("DATE_BIRTH", surfer.getDate_birth());

        String[] param = new String[1];
        param[0] = String.valueOf(surfer.getId());
        try {
            verifica();
            return conection.update("SURFER", contentValues, "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public ArrayList<Surfer> all() {
        ArrayList<Surfer> surfers = new ArrayList<>();
        try {
            verifica();
            String sql = "SELECT * FROM SURFER ORDER BY ID ASC";
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                do {
                    Surfer surfer = new Surfer();
                    surfer.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                    surfer.setName(results.getString(results.getColumnIndexOrThrow("NAME")));
                    surfer.setCountry(results.getString(results.getColumnIndexOrThrow("COUNTRY")));
                    surfer.setDate_birth(results.getString(results.getColumnIndexOrThrow("DATE_BIRTH")));
                    surfers.add(surfer);

                } while (results.moveToNext());
                results.close();
                return surfers;
            } else
                return surfers;
        } catch (SQLException ex) {
            return surfers;
        } catch (NullPointerException ex) {
            return surfers;
        }
    }

    public  Surfer find(int id) {
        Surfer surfer = new Surfer();
        try {
            verifica();
            String sql = "SELECT * FROM SURFER WHERE ID = " + String.valueOf(id) + "";
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                surfer.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                surfer.setName(results.getString(results.getColumnIndexOrThrow("NAME")));
                surfer.setCountry(results.getString(results.getColumnIndexOrThrow("COUNTRY")));
                surfer.setDate_birth(results.getString(results.getColumnIndexOrThrow("DATE_BIRTH")));
                results.close();
                return surfer;
            } else
                return surfer;
        } catch (SQLException ex) {
            return surfer;
        } catch (NullPointerException ex) {
            return surfer;
        }
    }

}
