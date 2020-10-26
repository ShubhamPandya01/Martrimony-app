package com.example.matrimony_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.example.matrimony_demo.R;
import com.example.matrimony_demo.model.CityModel;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends BaseAdapter {
    Context context;
    ArrayList<CityModel> cityList;

    public CityAdapter(Context context, ArrayList<CityModel> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.view_row_text, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.tvFirstName.setText(cityList.get(i).getName());
        if (i == 0) {
            viewHolder.tvFirstName.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        } else {
            viewHolder.tvFirstName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        return v;
    }

    class ViewHolder {
        @BindView(R.id.tvFirstName)
        TextView tvFirstName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
