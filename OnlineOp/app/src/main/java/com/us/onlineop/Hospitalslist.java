package com.us.onlineop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.HosptialModel;

import java.util.ArrayList;
import java.util.List;

public class Hospitalslist extends AppCompatActivity {

    ListView listView;
    List<HosptialModel> hosptialModelList;
    boolean f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        listView=findViewById(R.id.lvlist);
       f=false;
        Bundle bundle=getIntent().getExtras();
        final String state=bundle.getString("state");
        final String city=bundle.getString("city");
        Log.e("state",state);
        Log.e("city",city);

        hosptialModelList=new ArrayList<>();
        DatabaseReference mdatabaseref= FirebaseDatabase.getInstance().getReference("ophospitals");
        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    HosptialModel hosptialModel=dataSnapshot1.getValue(HosptialModel.class);
                    Log.e("stat in",hosptialModel.getState());
                    Log.e("city in",hosptialModel.getCity());
                  if(hosptialModel.getState().equalsIgnoreCase(state) && hosptialModel.getCity().equalsIgnoreCase(city)) {
                      hosptialModel.setHospitalkey(dataSnapshot1.getKey());
                      hosptialModelList.add(hosptialModel);

                  }else{

                  }

                }
if(hosptialModelList.size()<0 || hosptialModelList==null){
    Toast.makeText(getApplicationContext(),"no hospitals found",Toast.LENGTH_SHORT).show();
}else {
    HospitalAdapter hospitalAdapter = new HospitalAdapter(Hospitalslist.this, hosptialModelList);
    listView.setAdapter(hospitalAdapter);
}

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
