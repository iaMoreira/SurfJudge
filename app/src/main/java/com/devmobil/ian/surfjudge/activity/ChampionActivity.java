package com.devmobil.ian.surfjudge.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.adapter.LineGridAdapter;
import com.devmobil.ian.surfjudge.controller.Champions;
import com.devmobil.ian.surfjudge.fragment.Champion1Fragment;
import com.devmobil.ian.surfjudge.fragment.Champion2Fragment;
import com.devmobil.ian.surfjudge.fragment.Champion3Fragment;
import com.devmobil.ian.surfjudge.model.Champion;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChampionActivity extends AppCompatActivity implements LineGridAdapter.onItemChangeListener {
    FragmentManager fm = getSupportFragmentManager();
    Champion1Fragment frag1;
    Champion2Fragment frag2;
    Champion3Fragment frag3;
    Fragment active;
    private int id = 0;
    private Champion champion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        Bundle bundle = getIntent().getExtras();
        Champions champions = new Champions(this);
        if ((bundle != null) && (bundle.containsKey("id"))) {
            id = bundle.getInt("id");
            champion = champions.find(id);
            frag1 = new Champion1Fragment(champion);
            frag2 = new Champion2Fragment(champion, this);
            frag3 = new Champion3Fragment();
            active = frag1;

        }
        actionBar.setTitle(champion.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            fm.beginTransaction().add(R.id.champion, frag3, "3").hide(frag3).commit();
            fm.beginTransaction().add(R.id.champion, frag2, "2").hide(frag2).commit();
            fm.beginTransaction().add(R.id.champion, frag1, "1").commit();
        }

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux;
                if(active == frag1){
                    fm.beginTransaction().hide(active).show(frag2).commit();
                    active = frag2;
                }else if(active == frag2){
                    fm.beginTransaction().hide(active).show(frag3).commit();
                    active = frag3;
                }else {
                }

            }
        });
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
                break;
            case R.id.action_delete:
                    Log.d("Champion", ""+id);
                    if (this.id > 0) {
                        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                        alt_bld.setTitle("Delete "+ champion.getTitle());
                        alt_bld.setMessage("If you delete the championship, this will result in the exclusion of all items related to it. Are you sure you want to delete this championship?");
                        alt_bld.setNegativeButton("Don't delete", null);
                        alt_bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                fim();
                            }
                        });
                        AlertDialog alert = alt_bld.create();
                        alert.show();


                    }
                return true;

        }

        return false;
    }

    private void alert(String aviso) {
        Toast.makeText(this, aviso, Toast.LENGTH_LONG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }

    public void fim() {
        new Champions(this).delete(this.id);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if( keyCode== KeyEvent.KEYCODE_BACK)
        {

            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }

        return super.onKeyDown(keyCode, event);

    }
    @Override
    public void onTextChanged(int col, int position, String s) {
        Log.d("onTextChanged", "col= "+ col + " pos= "+ position+ " "+ s);
        Double aux = 0.0;
        int ind = 0;
        for(ArrayList<Double> arrayList : frag2.mChampionAdapter.mData){
            if(arrayList.get(0) > 0.0) {
                aux += arrayList.get(0);
                ind++;
            }
        }
        aux /= ind;
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        frag2.textView.setText("Total: "+ decimalFormat.format(aux));
    }
}
