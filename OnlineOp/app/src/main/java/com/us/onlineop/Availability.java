package com.us.onlineop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.RegisterModel;
import com.us.onlineop.Models.Store;

import java.util.HashMap;

public class Availability extends AppCompatActivity {

    RadioGroup todayavail;
    RadioGroup tommorowavail;
    Button bttoday,bttommrow;
    RadioButton radioButton;
    HashMap map;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        todayavail=findViewById(R.id.todayavail);
        tommorowavail=findViewById(R.id.todayavail);
        bttoday=findViewById(R.id.bttoday);
        bttommrow=findViewById(R.id.bttommorow);
        map= Store.getUserDetails(this);
        final String docid=map.get("docid").toString();
        bttoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedid=todayavail.getCheckedRadioButtonId();
                radioButton=findViewById(selectedid);
             databaseReference= FirebaseDatabase.getInstance().getReference("opregister");
                Query query=databaseReference.orderByChild("id").equalTo(docid);
             query.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                         RegisterModel registerModel=dataSnapshot1.getValue(RegisterModel.class);
                         if(radioButton.getText().toString().equalsIgnoreCase("Available")) {
                             registerModel.setAvailability("1");
                             String userid = dataSnapshot1.getKey();
                             databaseReference.child(userid).setValue(registerModel);
                             Toast.makeText(getApplicationContext(),"Availabilty updated",Toast.LENGTH_SHORT).show();
                         }
                         if(radioButton.getText().toString().equalsIgnoreCase("not Available")) {
                             registerModel.setAvailability("0");
                             String userid = dataSnapshot1.getKey();
                             databaseReference.child(userid).setValue(registerModel);
                             Toast.makeText(getApplicationContext(),"Availabilty updated",Toast.LENGTH_SHORT).show();
                         }
                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {
                     Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
                 }
             });




            }
        });

        bttommrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedid=todayavail.getCheckedRadioButtonId();
                radioButton=findViewById(selectedid);
                databaseReference= FirebaseDatabase.getInstance().getReference("opregister");
                Query query=databaseReference.orderByChild("id").equalTo(docid);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            RegisterModel registerModel=dataSnapshot1.getValue(RegisterModel.class);
                            if(radioButton.getText().toString().equalsIgnoreCase("Available")) {
                                registerModel.setTommorowavailability("1");
                                String userid = dataSnapshot1.getKey();
                                databaseReference.child(userid).setValue(registerModel);
                                Toast.makeText(getApplicationContext(),"Availabilty updated",Toast.LENGTH_SHORT).show();
                            }
                            if(radioButton.getText().toString().equalsIgnoreCase("not Available")) {
                                registerModel.setTommorowavailability("0");
                                String userid = dataSnapshot1.getKey();
                                databaseReference.child(userid).setValue(registerModel);
                                Toast.makeText(getApplicationContext(),"Availabilty updated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
