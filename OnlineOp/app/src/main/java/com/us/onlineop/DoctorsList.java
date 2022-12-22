package com.us.onlineop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;
import com.us.onlineop.Models.RegisterModel;
import com.us.onlineop.Models.Store;

import java.util.ArrayList;
import java.util.List;

public class DoctorsList extends AppCompatActivity {

    ListView lvlist;
    List<DoctorModel> doctorModelList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctros_list);
doctorModelList=new ArrayList<>();
    lvlist=findViewById(R.id.lvlist);
    Bundle bundle=getIntent().getExtras();
    String hospid=bundle.getString("hosid");

      DatabaseReference  mdatabaseref= FirebaseDatabase.getInstance().getReference("opdoctors");
        Query query=mdatabaseref.orderByChild("hospid").equalTo(hospid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                       DoctorModel registerModel =dataSnapshot1.getValue(DoctorModel.class);
                       doctorModelList.add(registerModel);





                }
                DoctorOpAdapter doctorOpAdapter=new DoctorOpAdapter(DoctorsList.this,doctorModelList);
                    lvlist.setAdapter(doctorOpAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                ToastHelper.toastMsg(getApplicationContext(),databaseError.toString());
            }
        });
    }
}
