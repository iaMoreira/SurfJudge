package com.devmobil.ian.surfjudge.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.devmobil.ian.surfjudge.R;

public class CreateChampion extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_champion);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Champion");

        spinner = findViewById(R.id.spinner);
        Spinner spinner = findViewById(R.id.spinner);
        String[] aux = new String[]{"Open", "Long"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aux);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}
