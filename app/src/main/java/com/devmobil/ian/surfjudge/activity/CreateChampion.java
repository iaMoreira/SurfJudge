package com.devmobil.ian.surfjudge.activity;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.fragment.Cad1Fragment;
import com.devmobil.ian.surfjudge.fragment.Cad2Fragment;
import com.devmobil.ian.surfjudge.fragment.Cad3Fragment;

import java.util.Objects;

public class CreateChampion extends AppCompatActivity  {
    FragmentManager fm = getSupportFragmentManager();
    Cad1Fragment frag1 = new Cad1Fragment();
    Cad2Fragment frag2 = new Cad2Fragment();
    Cad3Fragment frag3 = new Cad3Fragment();
    Fragment active = frag1;


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
                }else if(active == frag2){
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
        finish();
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
        Snackbar.make(Objects.requireNonNull(getCurrentFocus()), aviso, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
