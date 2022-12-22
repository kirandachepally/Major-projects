package com.us.onlineop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

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

public class DashBoard  extends AppCompatActivity {
    List<Opusers> opusersList;
    ListView lvlist;
    TextView tvpatid,tvdocid,tvsymptoms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        HashMap map= Store.getUserDetails(this);
        String userid=map.get("docid").toString();
        tvpatid=findViewById(R.id.tvpid);
        tvdocid=findViewById(R.id.tvdocid);
       // tvsymptoms.findViewById(R.id.tvsymptoms);
        lvlist=findViewById(R.id.lvlist);
       opusersList=new ArrayList<>();
        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("opusers");
        Query query1=databaseReference1.orderByChild("opuseridkey").equalTo(userid);
       query1.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                   Opusers opusers=dataSnapshot1.getValue(Opusers.class);
                   tvpatid.setText("your op is"+String.valueOf(opusers.getOpid()));
                   tvdocid.setText("doctor id is"+opusers.getDocid()+"\n"+"symptoms\t\t"+opusers.getSymptoms());
//                   tvsymptoms.setText(opusers.getSymptoms());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("opusers");
        Query query=databaseReference.orderByChild("opuseridkey").equalTo(userid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Opusers opusers=dataSnapshot1.getValue(Opusers.class);
                    DatabaseReference mdatabaseref=FirebaseDatabase.getInstance().getReference("opusers");
                    Query query=mdatabaseref.orderByChild("docid").equalTo(opusers.getDocid());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
                                    Opusers opusers=dataSnapshot1.getValue(Opusers.class);
                                    opusersList.add(opusers);
                                }

                                OpAdapter1 opAdapter=new OpAdapter1(DashBoard.this,opusersList);
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
