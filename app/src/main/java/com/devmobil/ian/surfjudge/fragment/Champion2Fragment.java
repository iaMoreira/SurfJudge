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
import com.devmobil.ian.surfjudge.controller.ChampionSurfers;
import com.devmobil.ian.surfjudge.controller.Champions;
import com.devmobil.ian.surfjudge.controller.Notes;
import com.devmobil.ian.surfjudge.controller.Surfers;
import com.devmobil.ian.surfjudge.model.Champion;
import com.devmobil.ian.surfjudge.model.ChampionSurfer;
import com.devmobil.ian.surfjudge.model.Surfer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    public TextView txtOne;
    public TextView txtTwo;
    public TextView txtThree;
    public TextView txtFour;
    public TextView txtPosition1;
    public TextView txtPosition2;
    public TextView txtPosition3;
    public TextView txtPosition4;
    public TextView txtNeeds1;
    public TextView txtNeeds2;
    public TextView txtNeeds3;
    public TextView txtNeeds4;
    public ChampionSurfers championSurfers;
    public ArrayList<Double> listNote1;
    public ArrayList<Double> listNote2;
    public ArrayList<Double> listNote3;
    public ArrayList<Double> listNote4;
    public LinearLayout linearLayout1;
    public LinearLayout linearLayout2;
    public LinearLayout linearLayout3;
    public LinearLayout linearLayout4;
    public ArrayList<Surfer> listSurfers = new ArrayList<>();
    public ArrayList<ChampionSurfer> listChampionsSurfers;
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

    @SuppressLint("ValidFragment")
    public Champion2Fragment(Champion c, LineGridAdapter.onItemChangeListener textWatcher) {
        champion = c;
        this.textWatcher = textWatcher;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_champion2, container, false);
        txtOne = root.findViewById(R.id.txtOne);
        txtTwo = root.findViewById(R.id.txtTwo);
        txtThree = root.findViewById(R.id.txtThree);
        txtFour = root.findViewById(R.id.txtFour);
        linearLayout1 = root.findViewById(R.id.linear1);
        linearLayout2 = root.findViewById(R.id.linear2);
        linearLayout3 = root.findViewById(R.id.linear3);
        linearLayout4 = root.findViewById(R.id.linear4);

        txtPosition1 = root.findViewById(R.id.txtPosition1);
        txtPosition2 = root.findViewById(R.id.txtPosition2);
        txtPosition3 = root.findViewById(R.id.txtPosition3);
        txtPosition4 = root.findViewById(R.id.txtPosition4);

        txtNeeds1 = root.findViewById(R.id.txtNeeds1);
        txtNeeds2 = root.findViewById(R.id.txtNeeds2);
        txtNeeds3 = root.findViewById(R.id.txtNeeds3);
        txtNeeds4 = root.findViewById(R.id.txtNeeds4);
        Notes notes = new Notes(getContext());
        Surfers aux = new Surfers(getContext());
        championSurfers = new ChampionSurfers(getContext());
        listChampionsSurfers = championSurfers.all(champion.getId());
        for(ChampionSurfer championSurfer : listChampionsSurfers){
            Surfer surfer = aux.find(championSurfer.getSurfer_id());
            surfer.setColor(championSurfer.getColor());
            if(listSurfers.size() == 0){
                linearLayout1.setBackgroundColor(championSurfer.getColor());
                listNote1 = notes.allNotes(championSurfer.getId());
                linearLayout1.setVisibility(View.VISIBLE);
            }else if(listSurfers.size() == 1){
                linearLayout2.setBackgroundColor(championSurfer.getColor());
                linearLayout2.setVisibility(View.VISIBLE);
                listNote2 = notes.allNotes(championSurfer.getId());
            }else if(listSurfers.size() == 2){
                linearLayout3.setBackgroundColor(championSurfer.getColor());
                linearLayout3.setVisibility(View.VISIBLE);
                listNote3 = notes.allNotes(championSurfer.getId());
            }else if(listSurfers.size() == 3){
                linearLayout4.setBackgroundColor(championSurfer.getColor());
                linearLayout4.setVisibility(View.VISIBLE);
                listNote4 = notes.allNotes(championSurfer.getId());
            }
            listSurfers.add(surfer);

        }
        recyclerView = root.findViewById(R.id.recyclerView);
        List<ArrayList<Double>> array = new  ArrayList<>();
        for (int i = 0; i < listNote1.size(); i++) {
            ArrayList<Double> listAux = new ArrayList<>();
            if(listSurfers.size() > 0) {
                listAux.add(listNote1.get(i));
            }
            if(listSurfers.size() > 1) {
                listAux.add(listNote2.get(i));
            }
            if(listSurfers.size() > 2) {
                listAux.add(listNote3.get(i));
            }
            if(listSurfers.size() > 3) {
                listAux.add(listNote4.get(i));
            }

            array.add(listAux);
        }
        mChampionAdapter = new LineGridAdapter(getContext(),array, txtOne.getWidth());
        mChampionAdapter.setHasStableIds(true);
        mChampionAdapter.setOnItemClickListener( textWatcher );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mChampionAdapter);
        aux1 = new ArrayList<>();
        aux2 = new ArrayList<>();
        aux3 = new ArrayList<>();
        aux4 = new ArrayList<>();
        sum1 = 0.0;
        sum2 = 0.0;
        sum3 = 0.0;
        sum4 = 0.0;
        txtPosition1.setText("");
        txtPosition2.setText("");
        txtPosition3.setText("");
        txtPosition4.setText("");
        for(ArrayList<Double> arrayList : array){
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

        txtOne.setText("Total: "+ decimalFormat.format(sum1));
        txtTwo.setText("Total: "+ decimalFormat.format(sum2));
        txtThree.setText("Total: "+ decimalFormat.format(sum3));
        txtFour.setText("Total: "+ decimalFormat.format(sum4));

        return root;
    }

    public  void sort(int index){
        if(positions.get(index).equals(sum1) && txtPosition1.getText().toString().isEmpty()){
            txtPosition1.setText((index + 1) + "º");
            needs(index, txtNeeds1);
        }else if(positions.get(index).equals(sum2) && txtPosition2.getText().toString().isEmpty()){
            txtPosition2.setText((index + 1) + "º");
            needs(index, txtNeeds2);
        }else if(positions.get(index).equals(sum3) && txtPosition3.getText().toString().isEmpty()){
            txtPosition3.setText((index + 1) + "º");
            needs(index, txtNeeds3);
        }else if(positions.get(index).equals(sum4) && txtPosition4.getText().toString().isEmpty()){
            txtPosition4.setText((index + 1) + "º");
            needs(index, txtNeeds4);
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
