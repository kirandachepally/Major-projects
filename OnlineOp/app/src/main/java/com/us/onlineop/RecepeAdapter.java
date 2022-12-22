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
import com.us.onlineop.Models.RecepModel;

import java.util.List;

class RecepeAdapter  extends BaseAdapter {
    Context context;
    List<RecepModel> doctorModels;
    public RecepeAdapter(Context context, List<RecepModel> doctorModelList) {
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
        Button  btsubmit=convertView.findViewById(R.id.btsubmit);

        final RecepModel doctorModel=doctorModels.get(position);
       tvdocid.setText("Receptionist id:\t\t"+doctorModel.getRecepid());tvdocname.setText("Receptionist name:\t\t"+doctorModel.getRecepname());
btsubmit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference mdatabaseref= FirebaseDatabase.getInstance().getReference("oprecep");
        mdatabaseref.child(doctorModel.getRecepkey()).removeValue();
        ToastHelper.toastMsg(context,"Deleted successfully");
        Intent intent=new Intent(context,AdminSuccess.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
});


        return convertView;
    }
}
