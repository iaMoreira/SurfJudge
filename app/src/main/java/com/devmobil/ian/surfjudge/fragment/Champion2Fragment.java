package com.devmobil.ian.surfjudge.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.adapter.ChampionAdapter;
import com.devmobil.ian.surfjudge.adapter.LineGridAdapter;
import com.devmobil.ian.surfjudge.controller.Champions;
import com.devmobil.ian.surfjudge.model.Champion;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Champion2Fragment extends Fragment {

    private Champion champion;
    private RecyclerView recyclerView;
    @SuppressLint("ValidFragment")
    public Champion2Fragment(Champion c) {
        champion = c;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_champion2, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        List<Champion> array = new  ArrayList<>();
        for (int i = 0; i < champion.getWaves(); i++) {
            array.add(new Champion());
        }
        LineGridAdapter mChampionAdapter = new LineGridAdapter(getContext(),array );
        mChampionAdapter.setHasStableIds(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mChampionAdapter);

        return root;
    }

}
