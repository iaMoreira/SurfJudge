package com.devmobil.ian.surfjudge.fragment;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.adapter.SurfersAdapter;
import com.devmobil.ian.surfjudge.controller.ChampionSurfers;
import com.devmobil.ian.surfjudge.controller.Surfers;
import com.devmobil.ian.surfjudge.model.Champion;
import com.devmobil.ian.surfjudge.model.ChampionSurfer;
import com.devmobil.ian.surfjudge.model.Surfer;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Champion1Fragment extends Fragment {

    private Champion champion;
    public ImageView imageView;
    private TextView txtDescription;
    private TextView txtDateTime;
    private TextView txtCategory;
    private TextView txtWaves;
    private TextView txtPlace;
    public RecyclerView recyclerView;
    public ArrayList<Surfer> listSurfers = new ArrayList<>();
    public SurfersAdapter surfersAdapter;
    Surfer surfer;
    public Champion1Fragment(Champion c) {
        champion = c;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_champion1, container, false);
        imageView = root.findViewById(R.id.imgChampion);
        txtDescription = root.findViewById(R.id.txtDescription);
        txtDateTime = root.findViewById(R.id.txtDateTime);
        txtCategory = root.findViewById(R.id.txtCategory);
        txtWaves = root.findViewById(R.id.txtWaves);txtWaves = root.findViewById(R.id.txtWaves);
        txtPlace = root.findViewById(R.id.txtPlace);

        if (champion.getImage() != null) {
            Glide.with(getContext()).load(champion.getImage()).into(imageView);
        }        txtDescription.setText(champion.getDescription());
        txtDateTime.setText(champion.getDate_time());
        txtCategory.setText(champion.getCategory());
        txtWaves.setText(String.valueOf(champion.getWaves()));
        txtPlace.setText(champion.getPlace());
        Surfers aux = new Surfers(this.getContext());
        ChampionSurfers championSurfers = new ChampionSurfers(getContext());
        for(ChampionSurfer championSurfer : championSurfers.all(champion.getId())){
            Surfer surfer = aux.find(championSurfer.getSurfer_id());
            surfer.setColor(championSurfer.getColor());
            listSurfers.add(surfer);
        }
        recyclerView = root.findViewById(R.id.recyclerView);
        surfersAdapter = new SurfersAdapter(getActivity(), listSurfers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(surfersAdapter);

        return root;
    }

}
