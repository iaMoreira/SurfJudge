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
import android.widget.TextView;
import android.widget.Toast;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.adapter.LineGridAdapter;
import com.devmobil.ian.surfjudge.controller.ChampionSurfers;
import com.devmobil.ian.surfjudge.controller.Champions;
import com.devmobil.ian.surfjudge.controller.Notes;
import com.devmobil.ian.surfjudge.controller.Surfers;
import com.devmobil.ian.surfjudge.fragment.Champion1Fragment;
import com.devmobil.ian.surfjudge.fragment.Champion2Fragment;
import com.devmobil.ian.surfjudge.fragment.Champion3Fragment;
import com.devmobil.ian.surfjudge.model.Champion;
import com.devmobil.ian.surfjudge.model.ChampionSurfer;
import com.devmobil.ian.surfjudge.model.Note;
import com.devmobil.ian.surfjudge.model.Surfer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ChampionActivity extends AppCompatActivity implements LineGridAdapter.onItemChangeListener {
    FragmentManager fm = getSupportFragmentManager();
    Champion1Fragment frag1;
    Champion2Fragment frag2;
    Champion3Fragment frag3;
    Fragment active;
    private int id = 0;
    private Champion champion;
    ArrayList<Double> positions;


    ArrayList<Double> aux1;
    ArrayList<Double> aux2;
    ArrayList<Double> aux3;
    ArrayList<Double> aux4;

    Double sum1 = 0.0;
    Double sum2 = 0.0;
    Double sum3 = 0.0;
    Double sum4 = 0.0;

    Notes notes;
    DecimalFormat decimalFormat = new DecimalFormat("0.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        notes = new Notes(this);
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
        aux1 = new ArrayList<>();
        aux2 = new ArrayList<>();
        aux3 = new ArrayList<>();
        aux4 = new ArrayList<>();
        sum1 = 0.0;
        sum2 = 0.0;
        sum3 = 0.0;
        sum4 = 0.0;

        Note note = notes.find(frag2.listChampionsSurfers.get(col).getId(), position+1);
        if(s.isEmpty()){
            s= "0";
        }
        note.setValue(Double.valueOf(s));
        notes.update(note);
        frag2.txtPosition1.setText("");
        frag2.txtPosition2.setText("");
        frag2.txtPosition3.setText("");
        frag2.txtPosition4.setText("");
        for(ArrayList<Double> arrayList : frag2.mChampionAdapter.mData){
            if(arrayList.size() > 0 && arrayList.get(0) > -1.0) {
                aux1.add(arrayList.get(0));
            }
            if(arrayList.size() > 1 && arrayList.get(1) > -1.0) {
                aux2.add(arrayList.get(1));
            }
            if(arrayList.size() > 2 && arrayList.get(2) > -1.0) {
                aux3.add(arrayList.get(2));
            }
            if( arrayList.size() > 3 && arrayList.get(3) > -1.0) {
                aux4.add(arrayList.get(3));
            }
        }

        Collections.sort(aux1, Collections.reverseOrder());
        Collections.sort(aux2, Collections.reverseOrder());
        Collections.sort(aux3, Collections.reverseOrder());
        Collections.sort(aux4, Collections.reverseOrder());

        if(aux1.size() == 0){
            sum1 = 0.0;
        }else if(aux1.size() == 1){
            sum1 = aux1.get(0);
        }else {
            sum1 = aux1.get(0)+aux1.get(1);
        }

        if(aux2.size() == 0){
            sum2 = 0.0;
        }else if(aux2.size() == 1){
            sum2 = aux2.get(0);
        }else {
            sum2 = aux2.get(0)+aux2.get(1);
        }

        if(aux3.size() == 0){
            sum3 = 0.0;
        }else if(aux3.size() == 1){
            sum3 = aux3.get(0);
        }else {
            sum3 = aux3.get(0)+aux3.get(1);
        }

        if(aux4.size() == 0){
            sum4 = 0.0;
        }else if(aux4.size() == 1){
            sum4 = aux4.get(0);
        }else {
            sum4 = aux4.get(0)+aux4.get(1);
        }


        positions = new ArrayList();
        positions.add(sum1);
        positions.add(sum2);
        positions.add(sum3);
        positions.add(sum4);
        Collections.sort(positions, Collections.reverseOrder());
        for(int i = 0; i < 4; i++){
            sort(i);
        }

        frag2.txtOne.setText("Total: "+ decimalFormat.format(sum1));
        frag2.txtTwo.setText("Total: "+ decimalFormat.format(sum2));
        frag2.txtThree.setText("Total: "+ decimalFormat.format(sum3));
        frag2.txtFour.setText("Total: "+ decimalFormat.format(sum4));

    }
    public  void sort(int index){
        if(positions.get(index).equals(sum1) && frag2.txtPosition1.getText().toString().isEmpty()){
            frag2.txtPosition1.setText((index + 1) + "º");
            needs(index, frag2.txtNeeds1);
        }else if(positions.get(index).equals(sum2) && frag2.txtPosition2.getText().toString().isEmpty()){
            frag2.txtPosition2.setText((index + 1) + "º");
            needs(index, frag2.txtNeeds2);
        }else if(positions.get(index).equals(sum3) && frag2.txtPosition3.getText().toString().isEmpty()){
            frag2.txtPosition3.setText((index + 1) + "º");
            needs(index, frag2.txtNeeds3);
        }else if(positions.get(index).equals(sum4) && frag2.txtPosition4.getText().toString().isEmpty()){
            frag2.txtPosition4.setText((index + 1) + "º");
            needs(index, frag2.txtNeeds4);
        }

    }

    public  void needs(int index, TextView needs){
        if(index == 0){
            needs.setText("Needs: "+ 0.00);
        }else if(index == 1){
            needs.setText("Needs: "+ decimalFormat.format(positions.get(0) + 0.01 - positions.get(1)));
        }else if(index == 2){
            needs.setText("Needs: "+ decimalFormat.format(positions.get(1) + 0.01 - positions.get(2)));
        }else if(index == 3){
            needs.setText("Needs: "+ decimalFormat.format(positions.get(1) + 0.01 - positions.get(3)));
        }

    }
}
