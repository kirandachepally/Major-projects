package com.us.onlineop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.Opusers;
import com.us.onlineop.Models.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.spec.OAEPParameterSpec;

public class Get_Op_Doctor extends AppCompatActivity {

    ListView lvlist;
    List<Opusers> opusersList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_doctor);
        lvlist=findViewById(R.id.lvlist);
        opusersList=new ArrayList<>();
        HashMap hm= Store.getUserDetails(this);
        String id=hm.get("docid").toString();
        Log.e("doc id",id);
DatabaseReference mdatabaseref=FirebaseDatabase.getInstance().getReference("opusers");
Query query=mdatabaseref.orderByChild("docid").equalTo(id);

query.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()){
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
                Opusers opusers=dataSnapshot1.getValue(Opusers.class);
                opusersList.add(opusers);
            }

           OpAdapter opAdapter=new OpAdapter(Get_Op_Doctor.this,opusersList);
            lvlist.setAdapter(opAdapter);


        }else{
            ToastHelper.toastMsg(getApplicationContext(),"no op found");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    }
}
