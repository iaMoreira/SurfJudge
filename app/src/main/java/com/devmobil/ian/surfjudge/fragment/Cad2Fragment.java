package com.devmobil.ian.surfjudge.fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.devmobil.ian.surfjudge.R;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Cad2Fragment extends Fragment implements View.OnClickListener {

    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private TextView txtDate;
    private TextView txtHour;
    public  ImageView img;
    public EditText edtPlace;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cad2, container, false);
        txtDate = root.findViewById(R.id.txtDate);
        txtHour = root.findViewById(R.id.txtHour);
        edtPlace = root.findViewById(R.id.edtPlace);

        txtDate.setOnClickListener(this);
        txtHour.setOnClickListener(this);
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

                txtDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        img = root.findViewById(R.id.imgCad2);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtDate:
                new DatePickerDialog(Objects.requireNonNull(getContext()), R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.txtHour:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtHour.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
        }
    }

    public String validate (){

        if(txtDate.getText().toString().equals("")){
            return "Date is required! Please enter a date.";
        }else if(txtHour.getText().toString().equals("")){
            return "Hour is required! please enter a hour.\n";
        }else if(edtPlace.getText().toString().equals("")) {
            return "Place is mandatory! please insert a place.";
        }
        return "";
    }
}
