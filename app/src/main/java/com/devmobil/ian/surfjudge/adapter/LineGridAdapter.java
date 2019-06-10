package com.devmobil.ian.surfjudge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

public class LineGridAdapter extends RecyclerView.Adapter<LineGridAdapter.ChampionHolder> {
    @NonNull

    public List< ArrayList<Double>> mData;
    private LayoutInflater mInflater;
    public onItemChangeListener mItemChangeListener;
    private int width;
    public void setOnItemClickListener(onItemChangeListener mItemClickListener) {
        this.mItemChangeListener = mItemClickListener;
    }
    public interface onItemChangeListener {
        void onTextChanged(int col, int position, String s);
    }
    public LineGridAdapter(Context context, @NonNull List< ArrayList<Double>> data , int width) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.width = width;
    }

    @NonNull
    @Override
    public ChampionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_line, parent, false);
        return new ChampionHolder(view, mInflater.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionHolder holder, @SuppressLint("RecyclerView") final int position) {
       // Champion champion = mData.get(position);
        holder.txtPosition.setText(String.valueOf(position+1));
        ArrayList<Double> arrayList = new ArrayList<>(4);
        arrayList.add(0.0);
        arrayList.add(0.0);
        arrayList.add(0.0);
        arrayList.add(0.0);
        mData.set(position,arrayList);
        holder.edtColumn1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==0){
                    s= "0";
                }
                mData.get(position).set(0, Double.valueOf(s.toString()));
                if (mItemChangeListener != null) {
                    mItemChangeListener.onTextChanged(0, position, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edtColumn2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mData.get(position).set(1, Double.valueOf(s.toString()));
                if (mItemChangeListener != null) {
                    mItemChangeListener.onTextChanged(1, position, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edtColumn3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mData.get(position).set(2, Double.valueOf(s.toString()));
                if (mItemChangeListener != null) {
                    mItemChangeListener.onTextChanged(2, position, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.edtColumn4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mData.get(position).set(3, Double.valueOf(s.toString()));
                if (mItemChangeListener != null) {
                    mItemChangeListener.onTextChanged(3, position, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        public EditText edtColumn1;
        public EditText edtColumn2;
        public EditText edtColumn3;
        public EditText edtColumn4;


        ChampionHolder(View itemView, final Context context) {
            super(itemView);
            txtPosition = itemView.findViewById(R.id.txtPosition);
            edtColumn1 = itemView.findViewById(R.id.edtColumn1);
            edtColumn2 = itemView.findViewById(R.id.edtColumn2);
            edtColumn3 = itemView.findViewById(R.id.edtColumn3);
            edtColumn4 = itemView.findViewById(R.id.edtColumn4);
            edtColumn1.setMaxWidth(width);
            edtColumn2.setMaxWidth(width);
            edtColumn3.setMaxWidth(width);
            edtColumn4.setMaxWidth(width);
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
