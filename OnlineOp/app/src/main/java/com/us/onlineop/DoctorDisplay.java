package com.us.onlineop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.us.onlineop.Models.DoctorModel;

import java.util.List;

class DoctorDisplay  extends BaseAdapter {
    Context context;
    List<DoctorModel> doctorModelList;
    public DoctorDisplay(AddOp addOp, List<DoctorModel> doctorModelList) {
        this.context=addOp;
        this.doctorModelList=doctorModelList;
    }

    @Override
    public int getCount() {
        return doctorModelList.size();
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
        convertView= LayoutInflater.from(context).inflate(R.layout.activity_doc_row,null);
        TextView tvdocid=convertView.findViewById(R.id.docid);
        TextView tvdocname=convertView.findViewById(R.id.docname);
        TextView tvdocspec=convertView.findViewById(R.id.docspec);
        DoctorModel doctorModel=doctorModelList.get(position);
        tvdocid.setText("Doctor id:\t\t"+doctorModel.getDocid());
        tvdocname.setText("Doctor name:\t\t"+doctorModel.getDocname());
        tvdocspec.setText("Doctor spec:\t\t"+doctorModel.getDocspec());
        return convertView;
    }
}
