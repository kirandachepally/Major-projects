package com.us.onlineop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;

import java.util.List;

class DoctorsAdapter  extends BaseAdapter {
    Context context;
    List<DoctorModel> doctorModels;
    DatabaseReference mdatabaseref;
    public DoctorsAdapter(Context context, List<DoctorModel> doctorModelList) {
        this.context=context;
        this.doctorModels=doctorModelList;
    }

    @Override
    public int getCount() {
        return doctorModels.size();
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
        convertView= LayoutInflater.from(context).inflate(R.layout.row_doctors,null);
        TextView tvhosid=convertView.findViewById(R.id.tvhosid);
        TextView tvdocid=convertView.findViewById(R.id.tvdocid);
        TextView tvdocname=convertView.findViewById(R.id.tvdocname);
        TextView tvdocspec=convertView.findViewById(R.id.tvspec);
        Button btsubmit=convertView.findViewById(R.id.btsubmit);

        final DoctorModel doctorModel=doctorModels.get(position);
       tvdocid.setText("Doctor Id:\t\t"+doctorModel.getDocid());tvdocname.setText("Doctor Name:\t\t"+doctorModel.getDocname());tvdocspec.setText("Specification:\t\t"+doctorModel.getDocspec());
btsubmit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mdatabaseref= FirebaseDatabase.getInstance().getReference("opdoctors");
        mdatabaseref.child(doctorModel.getDockey()).removeValue();
        ToastHelper.toastMsg(context,"docotor delted successfully");
      Intent intent=new Intent(context,AdminSuccess.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

    }
});

        return convertView;
    }
}
