package com.devmobil.ian.surfjudge.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devmobil.ian.surfjudge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cad3Fragment extends Fragment  implements View.OnClickListener {

    public ImageView img;

    public Cad3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cad3, container, false);
        root.findViewById(R.id.plusJudge).setOnClickListener(this);
        root.findViewById(R.id.plusSurfer).setOnClickListener(this);
        img = root.findViewById(R.id.imgCad3);

        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.plusJudge:

                break;
            case R.id.plusSurfer:

                break;
        }

    }

    public String validate (){

//        if(txtDate.getText().toString().equals("")){
//            return "Date is required! Please enter a date.";
//        }else if(txtHour.getText().toString().equals("")){
//            return "Hour is required! please enter a hour.\n";
//        }else if(edtPlace.getText().toString().equals("")) {
//            return "Place is mandatory! please insert a place.";
//        }
        return "";
    }
}
