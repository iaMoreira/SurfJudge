package com.devmobil.ian.surfjudge.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.model.Surfer;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SurfersAdapter extends RecyclerView.Adapter<SurfersAdapter.ChampionHolder> {
    @NonNull

    public List<Surfer> mData;
    private LayoutInflater mInflater;

    public SurfersAdapter(Context context, @NonNull List<Surfer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ChampionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_surfers, parent, false);
        return new ChampionHolder(view, mInflater.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionHolder holder, @SuppressLint("RecyclerView") final int position) {
        Surfer surfer = mData.get(position);
        holder.txtName.setText(surfer.getName());
        holder.txtCountry.setText(surfer.getCountry());
        ViewCompat.setBackgroundTintList(
                holder.linearColor,
                ColorStateList.valueOf(surfer.getColor()));

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    class ChampionHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearColor;
        public TextView txtName;
        public TextView txtCountry;


        ChampionHolder(View itemView, final Context context) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCountry = itemView.findViewById(R.id.txtCountry);
            linearColor = itemView.findViewById(R.id.linearColor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    FragmentManager manager = ((Activity) context).getFragmentManager();
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                    new SpectrumDialog.Builder(mInflater.getContext())
                            .setColors(R.array.demo_colors)
                            .setSelectedColorRes(R.color.red)
                            .setDismissOnColorSelected(true)
                            .setOutlineWidth(2)
                            .setTitle("Selecione uma cor")
                            .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                                @Override
                                public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                                    if (positiveResult) {
//                                        colors = color;
//                                        Drawable drawable = ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.item_adapter);
//                                        assert drawable != null;
//                                        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
//                                        linearColor.setBackground(drawable);
                                        ViewCompat.setBackgroundTintList(
                                                linearColor,
                                                ColorStateList.valueOf(color));
                                        mData.get(getLayoutPosition()).setColor(color);
                                    }
                                }
                            })
                            .build().show(manager, "Selecione uma cor");


                }
            });
        }
    }
}
