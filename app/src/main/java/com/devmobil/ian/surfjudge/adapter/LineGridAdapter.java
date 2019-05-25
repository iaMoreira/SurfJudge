package com.devmobil.ian.surfjudge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.activity.ChampionActivity;
import com.devmobil.ian.surfjudge.model.Champion;

import java.util.List;

public class LineGridAdapter extends RecyclerView.Adapter<LineGridAdapter.ChampionHolder> {
    @NonNull

    private List<Champion> mData;
    private LayoutInflater mInflater;

    public LineGridAdapter(Context context, @NonNull List<Champion> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ChampionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_line, parent, false);
        return new ChampionHolder(view, mInflater.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionHolder holder, int position) {
        Champion champion = mData.get(position);
        holder.txtPosition.setText(String.valueOf(position+1));
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
        TextView txtPosition;
        EditText edtColumn1;
        EditText edtColumn2;
        EditText edtColumn3;
        EditText edtColumn4;


        ChampionHolder(View itemView, final Context context) {
            super(itemView);
            txtPosition = itemView.findViewById(R.id.txtPosition);
            edtColumn1 = itemView.findViewById(R.id.edtColumn1);
            edtColumn2 = itemView.findViewById(R.id.edtColumn2);
            edtColumn3 = itemView.findViewById(R.id.edtColumn3);
            edtColumn4 = itemView.findViewById(R.id.edtColumn4);
            edtColumn1.setMaxWidth(edtColumn1.getWidth());
            edtColumn2.setMaxWidth(edtColumn1.getWidth());
            edtColumn3.setMaxWidth(edtColumn1.getWidth());
            edtColumn4.setMaxWidth(edtColumn1.getWidth());
            edtColumn1.setFilters(new InputFilter[]{new CustomRangeInputFilter(0f, 10.0f)});
            edtColumn2.setFilters(new InputFilter[]{new CustomRangeInputFilter(0f, 10.0f)});
            edtColumn3.setFilters(new InputFilter[]{new CustomRangeInputFilter(0f, 10.0f)});
            edtColumn4.setFilters(new InputFilter[]{new CustomRangeInputFilter(0f, 10.0f)});

        }
    }
    public class CustomRangeInputFilter implements InputFilter {
        private double minValue;
        private double maxValue;

        CustomRangeInputFilter(double minVal, double maxVal) {
            this.minValue = minVal;
            this.maxValue = maxVal;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
            try {
                // Remove the string out of destination that is to be replaced
                double input;
                String newVal = dest.toString().substring(0, dStart) + dest.toString().substring(dEnd, dest.toString().length());
                newVal = newVal.substring(0, dStart) + source.toString() + newVal.substring(dStart, newVal.length());
                if (newVal == null || newVal.equals("")) {
                    input = 0.0;
                } else {
                    if(newVal.length() > 4){
                        return "";
                    }
                    input = Double.parseDouble(newVal);
                }

                if (isInRange(minValue, maxValue, input)) {
                    return null;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return "";
        }

        private boolean isInRange(double a, double b, double c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
