package com.us.onlineop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;
import com.us.onlineop.Models.RegisterModel;

import java.util.List;

class DoctorOpAdapter  extends BaseAdapter {
    Context context;
    List<DoctorModel> doctorModels;
    DatabaseReference mdatabaseref;
    public DoctorOpAdapter(DoctorsList doctorsList, List<DoctorModel> doctorModelList) {
    this.context=doctorsList;
    this.doctorModels=doctorModelList;
    mdatabaseref= FirebaseDatabase.getInstance().getReference("opregister");
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
        convertView= LayoutInflater.from(context).inflate(R.layout.doctor_row,null);
        TextView tvdocname=convertView.findViewById(R.id.docname);
TextView tvspec=convertView.findViewById(R.id.docspec);
        final TextView tvtoday=convertView.findViewById(R.id.today);
        final TextView tvtommorow=convertView.findViewById(R.id.tommorwo);
        Button btsubmit=convertView.findViewById(R.id.btsubmit);
        final DoctorModel doctorModel=doctorModels.get(position);
tvspec.setText("specilization:\t"+doctorModel.getDocspec());
        mdatabaseref.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                       RegisterModel registerModel=dataSnapshot1.getValue(RegisterModel.class);
                       if(registerModel.getAvailability().equalsIgnoreCase("0")){
                           tvtoday.setText("today\t\t"+"not Available");
                       } if(registerModel.getAvailability().equalsIgnoreCase("1")){
                           tvtoday.setText("todays \t\t"+"Available");
                       }

                       if(registerModel.getTommorowavailability().equalsIgnoreCase("0")){
                           tvtommorow.setText("Tommrrow \t\t"+"not Available");

                       } if(registerModel.getTommorowavailability().equalsIgnoreCase("1")){
                           tvtommorow.setText("Tommrrow  \t\t"+"Available");

                       }


                   }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        tvdocname.setText("Doctor Name:"+doctorModel.getDocname());
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,GetOpForm.class).putExtra("docid",doctorModel.getDocid()));
            }
        });


        return convertView;
    }
}
