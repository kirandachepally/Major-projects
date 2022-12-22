package com.us.onlineop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.HosptialModel;

import java.util.List;

class HospitalAdapter extends BaseAdapter {

    Context context;
    List<HosptialModel> hosptialModels;
    public HospitalAdapter(Context context, List<HosptialModel> hosptialModelList) {
    this.context=context;
    this.hosptialModels=hosptialModelList;

    }

    @Override
    public int getCount() {
        return hosptialModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.activity_row,null);
        TextView tvhospital=convertView.findViewById(R.id.tvhospital);
        final HosptialModel hosptialModel=hosptialModels.get(position);
        tvhospital.setText(hosptialModel.getHospitalname());

        tvhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             context.startActivity(new Intent(context,DoctorsList.class).putExtra("hosid",hosptialModel.getHospitalkey()));

            }
        });

        return convertView;
    }
}
