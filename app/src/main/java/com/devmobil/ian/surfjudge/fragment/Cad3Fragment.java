package com.devmobil.ian.surfjudge.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.adapter.SurfersAdapter;
import com.devmobil.ian.surfjudge.controller.Surfers;
import com.devmobil.ian.surfjudge.dialog.CreateSurferDialog;
import com.devmobil.ian.surfjudge.model.Surfer;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cad3Fragment extends Fragment  implements View.OnClickListener {

    public ImageView img;
    public RecyclerView recyclerView;
    public ArrayList<Surfer> listSurfers = new ArrayList<>();
    public SurfersAdapter surfersAdapter;
    Surfer surfer;
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
        recyclerView = root.findViewById(R.id.recyclerView);

        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.plusJudge:

                break;
            case R.id.plusSurfer:
                final Surfers aux = new Surfers(this.getContext());
                final ArrayList<Surfer> surfers = aux.all();

                String[] nomes = new String[0];
                if (surfers != null && surfers.size() > 0) {
                    nomes = new String[surfers.size()];
                    for (int i = 0; i < surfers.size(); i++) {
                        nomes[i] = surfers.get(i).getName();
                    }
                }


                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(this.getContext());
                alt_bld.setIcon(R .drawable.ic_person_black_24dp);
                alt_bld.setTitle("Choice a surfer");
                alt_bld.setSingleChoiceItems(nomes, -1, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        surfer = surfers.get(item);
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int[] rainbow = getContext().getResources().getIntArray(R.array.demo_colors);
                        surfer.setColor(rainbow[listSurfers.size()]);
                        listSurfers.add(surfer);
                        surfersAdapter = new SurfersAdapter(getActivity(), listSurfers);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(surfersAdapter);
                    }
                })
                .setNegativeButton("Criar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(getApplicationContext(), CadastraProfessorActivity.class);
//                        startActivity(intent);
                        CreateSurferDialog dialog1 = new CreateSurferDialog();

                        dialog1.show(getActivity().getFragmentManager(),"s");
                    }
                });
                AlertDialog alert = alt_bld.create();
                alert.show();
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
