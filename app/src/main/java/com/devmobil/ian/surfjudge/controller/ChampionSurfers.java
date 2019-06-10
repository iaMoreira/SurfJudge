package com.devmobil.ian.surfjudge.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.devmobil.ian.surfjudge.database.Database;
import com.devmobil.ian.surfjudge.model.ChampionSurfer;

import java.util.ArrayList;

public class ChampionSurfers {

    private Context context;
    private SQLiteDatabase conection;

    public ChampionSurfers(Context context)
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

    public long insert(ChampionSurfer championSurfer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CHAMPION_ID", championSurfer.getChampion_id());
        contentValues.put("SURFER_ID", championSurfer.getSurfer_id());
        contentValues.put("COLOR", championSurfer.getColor());

        try {
            verifica();
            return conection.insertOrThrow("CHAMPION_SURFER", null, contentValues);
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
            return conection.delete("CHAMPION_SURFER", "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public int update(ChampionSurfer championSurfer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CHAMPION_ID", championSurfer.getChampion_id());
        contentValues.put("SURFER_ID", championSurfer.getSurfer_id());
        contentValues.put("COLOR", championSurfer.getColor());

        String[] param = new String[1];
        param[0] = String.valueOf(championSurfer.getId());
        try {
            verifica();
            return conection.update("CHAMPION_SURFER", contentValues, "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public ArrayList<ChampionSurfer> all(int id) {
        ArrayList<ChampionSurfer> championSurfers = new ArrayList<>();
        try {
            verifica();
            String sql = "SELECT * FROM CHAMPION_SURFER WHERE CHAMPION_ID = "+ id;
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                do {
                    ChampionSurfer championSurfer = new ChampionSurfer();
                    championSurfer.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                    championSurfer.setChampion_id(results.getInt(results.getColumnIndexOrThrow("CHAMPION_ID")));
                    championSurfer.setSurfer_id(results.getInt(results.getColumnIndexOrThrow("SURFER_ID")));
                    championSurfer.setColor(results.getInt(results.getColumnIndexOrThrow("COLOR")));
                    championSurfers.add(championSurfer);

                } while (results.moveToNext());
                results.close();
                return championSurfers;
            } else
                return championSurfers;
        } catch (SQLException ex) {
            return championSurfers;
        } catch (NullPointerException ex) {
            return championSurfers;
        }
    }

    public ChampionSurfer find(int id) {
        ChampionSurfer championSurfer = new ChampionSurfer();
        try {
            verifica();
            String sql = "SELECT * FROM CHAMPION_SURFER WHERE ID = " + String.valueOf(id) + "";
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                championSurfer.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                championSurfer.setChampion_id(results.getInt(results.getColumnIndexOrThrow("CHAMPION_ID")));
                championSurfer.setSurfer_id(results.getInt(results.getColumnIndexOrThrow("SURFER_ID")));
                championSurfer.setColor(results.getInt(results.getColumnIndexOrThrow("COLOR")));
                results.close();
                return championSurfer;
            } else
                return championSurfer;
        } catch (SQLException ex) {
            return championSurfer;
        } catch (NullPointerException ex) {
            return championSurfer;
        }
    }

}
