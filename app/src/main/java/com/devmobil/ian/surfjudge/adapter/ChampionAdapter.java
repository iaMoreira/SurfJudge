package com.devmobil.ian.surfjudge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.model.Champion;

import java.util.List;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ChampionHolder> {
    @NonNull

    private List<Champion> mData;
    private LayoutInflater mInflater;

    public ChampionAdapter(Context context, @NonNull List<Champion> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ChampionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_card, parent, false);
        return new ChampionHolder(view, mInflater.getContext());
    }

    // binds the data to the TextView in each row
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChampionHolder holder, int position) {
        Champion champion = mData.get(position);
        holder.txtTitle.setText(champion.getTitle());
        holder.txtDescription.setText(champion.getDescription());
        holder.txtDate.setText(champion.getDate_time());

        if (champion.getImage() != null) {
            Glide.with(mInflater.getContext()).load(champion.getImage()).into(holder.imageView);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ChampionHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtDate;
        ImageView imageView;

        ChampionHolder(View itemView, final Context context) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDate = itemView.findViewById(R.id.txtDate);
            imageView = itemView.findViewById(R.id.imgCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.size() > 0) {
//                        Intent it = new Intent(context, ChampionActivity.class);
//                        it.putExtra("id", mData.get(getLayoutPosition()).getId());
//                        context.startActivity(it);
                    }
                }
            });

        }
    }
}
