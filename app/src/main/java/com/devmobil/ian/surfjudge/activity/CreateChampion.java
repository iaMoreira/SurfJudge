package com.devmobil.ian.surfjudge.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.adapter.SurfersAdapter;
import com.devmobil.ian.surfjudge.controller.ChampionSurfers;
import com.devmobil.ian.surfjudge.controller.Champions;
import com.devmobil.ian.surfjudge.controller.Notes;
import com.devmobil.ian.surfjudge.dialog.CreateSurferDialog;
import com.devmobil.ian.surfjudge.fragment.Cad1Fragment;
import com.devmobil.ian.surfjudge.fragment.Cad2Fragment;
import com.devmobil.ian.surfjudge.fragment.Cad3Fragment;
import com.devmobil.ian.surfjudge.model.Champion;
import com.devmobil.ian.surfjudge.model.ChampionSurfer;
import com.devmobil.ian.surfjudge.model.Note;
import com.devmobil.ian.surfjudge.model.Surfer;

import java.util.Objects;

public class CreateChampion extends AppCompatActivity implements CreateSurferDialog.NoticeDialogListener  {
    FragmentManager fm = getSupportFragmentManager();
    Cad1Fragment frag1 = new Cad1Fragment();
    Cad2Fragment frag2 = new Cad2Fragment();
    Cad3Fragment frag3 = new Cad3Fragment();
    Fragment active = frag1;
    private int id = 0;
    private Champion champion;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_champion);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Champion");
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            fm.beginTransaction().add(R.id.createChampion, frag3, "3").hide(frag3).commit();
            fm.beginTransaction().add(R.id.createChampion, frag2, "2").hide(frag2).commit();
            fm.beginTransaction().add(R.id.createChampion, frag1, "1").commit();
        }

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String aux;
                if(active == frag1){
                    aux = frag1.validate();
                    if(!aux.equals("")){
                        alert(aux);
                        return;
                    }
                    fm.beginTransaction().hide(active).show(frag2).commit();
                    active = frag2;
                    if(frag1.uri != null)
                        frag2.img.setImageURI(frag1.uri);
                } else if(active == frag2){
                    aux = frag2.validate();
                    if(!aux.equals("")){
                        alert(aux);
                        return;
                    }
                    fm.beginTransaction().hide(active).show(frag3).commit();
                    active = frag3;
                    if(frag1.uri != null)
                        frag3.img.setImageURI(frag1.uri);
                }else {
                    aux = frag3.validate();
                    if(!aux.equals("")){
                        alert(aux);
                        return;
                    }
                    insert();
                }

            }
        });
//        fab.setVisibility(View.GONE);

    }

    private void insert() {
        try {
            Notes notes = new Notes(this);
            champion = new Champion();
            champion.setTitle(frag1.edtTitle.getText().toString());
            champion.setDescription(frag1.edtDescription.getText().toString());
            champion.setWaves(Integer.valueOf(frag1.edtWaves.getText().toString()));
            champion.setCategory(frag1.spinner.getSelectedItem().toString());
            if(frag1.uri != null)
            champion.setImage(frag1.uri.toString());
            champion.setDate_time(frag2.txtDate.getText().toString() + " - " + frag2.txtHour.getText().toString());
            champion.setPlace(frag2.edtPlace.getText().toString());
//            champion.set(frag3.getText().toString());
            Champions champions = new Champions(this);
            if (id == 0) {
                id = (int) champions.insert(champion);
                Log.d("ID", "" + id);

            } else {
                champion.setId(id);

                id = champions.update(champion);
            }
            ChampionSurfers championSurfers = new ChampionSurfers(this);
            for(Surfer surfer : frag3.listSurfers){
                ChampionSurfer championSurfer = new ChampionSurfer();
                championSurfer.setColor(surfer.getColor());
                championSurfer.setChampion_id(id);
                championSurfer.setSurfer_id(surfer.getId());
                int id = (int) championSurfers.insert(championSurfer);
                for(int i = 0; i < champion.getWaves(); i++){
                    Note note = new Note();
                    note.setValue(-1.0);
                    note.setWave(i+1);
                    note.setChampion_surver_id(id);
                    notes.insert(note);
                }
            }
            finish();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if(active == frag2){
                    fm.beginTransaction().hide(active).show(frag1).commit();
                    active = frag1;
                }else if(active == frag3){
                    fm.beginTransaction().hide(active).show(frag2).commit();
                    active = frag2;
                }else if(active == frag1){
                    finish();
                }
                return true;
        }

        return false;
    }

    private void alert(String aviso) {
        Toast.makeText(this, aviso, Toast.LENGTH_LONG).show();
    }
    public void onDialogPositiveClick(Surfer surfer) {
        int[] rainbow = this.getResources().getIntArray(R.array.demo_colors);
        surfer.setColor(rainbow[frag3.listSurfers.size()]);
        frag3.listSurfers.add(surfer);
        frag3.surfersAdapter = new SurfersAdapter(frag3.getContext(), frag3.listSurfers);
        frag3.recyclerView.setLayoutManager(new LinearLayoutManager(frag3.getContext()));
        frag3.recyclerView.setAdapter(frag3.surfersAdapter);
    }
}
