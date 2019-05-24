package com.devmobil.ian.surfjudge.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.devmobil.ian.surfjudge.database.Database;
import com.devmobil.ian.surfjudge.model.Note;

import java.util.ArrayList;

public class Notes {

    private Context context;
    private SQLiteDatabase conection;

    public Notes(Context context)
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

    public long insert(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("VALUE", note.getValue());
        contentValues.put("CHAMPION_SURFER_ID", note.getChampion_surver_id());
        contentValues.put("WAVE", note.getWave());

        try {
            verifica();
            return conection.insertOrThrow("NOTE", null, contentValues);
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
            return conection.delete("NOTE", "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public int update(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("VALUE", note.getValue());
        contentValues.put("CHAMPION_SURFER_ID", note.getChampion_surver_id());
        contentValues.put("WAVE", note.getWave());

        String[] param = new String[1];
        param[0] = String.valueOf(note.getId());
        try {
            verifica();
            return conection.update("NOTE", contentValues, "ID = ?", param);
        } catch (SQLException ex) {
            return 0;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public ArrayList<Note> all() {
        ArrayList<Note> notes = new ArrayList<>();
        try {
            verifica();
            String sql = "SELECT * FROM NOTE ORDER BY ID ASC";
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                do {
                    Note note = new Note();
                    note.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                    note.setValue(results.getFloat(results.getColumnIndexOrThrow("VALUE")));
                    note.setChampion_surver_id(results.getInt(results.getColumnIndexOrThrow("CHAMPION_SURFER_ID")));
                    note.setWave(results.getInt(results.getColumnIndexOrThrow("WAVE")));
                    notes.add(note);

                } while (results.moveToNext());
                results.close();
                return notes;
            } else
                return notes;
        } catch (SQLException ex) {
            return notes;
        } catch (NullPointerException ex) {
            return notes;
        }
    }

    public Note find(int id) {
        Note note = new Note();
        try {
            verifica();
            String sql = "SELECT * FROM NOTE WHERE ID = " + String.valueOf(id) + "";
            Cursor results = conection.rawQuery(sql, null);
            if (results.getCount() > 0) {
                results.moveToFirst();

                note.setId(results.getInt(results.getColumnIndexOrThrow("ID")));
                note.setValue(results.getFloat(results.getColumnIndexOrThrow("VALUE")));
                note.setChampion_surver_id(results.getInt(results.getColumnIndexOrThrow("CHAMPION_SURFER_ID")));
                note.setWave(results.getInt(results.getColumnIndexOrThrow("WAVE")));
                results.close();
                return note;
            } else
                return note;
        } catch (SQLException ex) {
            return note;
        } catch (NullPointerException ex) {
            return note;
        }
    }

}
