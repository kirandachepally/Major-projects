package com.us.onlineop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;
import com.us.onlineop.Models.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewDoctors extends AppCompatActivity {

    ListView lvlist;
    List<DoctorModel> doctorModelList;
    DatabaseReference databaseReference;
    Context context;
    HashMap map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_view_doctors);
        lvlist=findViewById(R.id.lvlist);
        doctorModelList=new ArrayList<>();
        context=getApplicationContext();
        map= Store.getUserDetails(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("opdoctors");
        Query query=databaseReference.orderByChild("hospid").equalTo(map.get("docid").toString());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String userkey=dataSnapshot1.getKey();
                    DoctorModel doctorModel=dataSnapshot1.getValue(DoctorModel.class);
                  doctorModel.setDockey(userkey);
                  doctorModelList.add(doctorModel);

                }

                DoctorsAdapter doctorsAdapter=new DoctorsAdapter(context,doctorModelList);
                lvlist.setAdapter(doctorsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
ToastHelper.toastMsg(getApplicationContext(),databaseError.toString());
            }
        });



    }
}
