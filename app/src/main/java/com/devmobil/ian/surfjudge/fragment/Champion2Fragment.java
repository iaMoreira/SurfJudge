package com.devmobil.ian.surfjudge.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private LineGridAdapter.onItemChangeListener textWatcher;
    public LineGridAdapter mChampionAdapter;
    public TextView textView;

    @SuppressLint("ValidFragment")
    public Champion2Fragment(Champion c, LineGridAdapter.onItemChangeListener textWatcher) {
        champion = c;
        this.textWatcher = textWatcher;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_champion2, container, false);
        textView = root.findViewById(R.id.txtOne);
        recyclerView = root.findViewById(R.id.recyclerView);
        List<ArrayList<Double>> array = new  ArrayList<>();
        for (int i = 0; i < champion.getWaves(); i++) {
            array.add(new ArrayList<Double>());
        }
        mChampionAdapter = new LineGridAdapter(getContext(),array, textView.getWidth());
        mChampionAdapter.setHasStableIds(true);
        mChampionAdapter.setOnItemClickListener( textWatcher );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mChampionAdapter);

        return root;
    }

}
