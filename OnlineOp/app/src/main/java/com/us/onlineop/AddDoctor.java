package com.us.onlineop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;
import com.us.onlineop.Models.Store;

import java.util.HashMap;

public class AddDoctor extends AppCompatActivity {
    EditText ethosid,etdocid,etdocname,etdocspec;
    DatabaseReference databaseReference;
    String userid;
    HashMap map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        ethosid=findViewById(R.id.ethosid);
        etdocid=findViewById(R.id.etdocid);
        etdocname=findViewById(R.id.etdocname);
        etdocspec=findViewById(R.id.etdocspec);
         map=Store.getUserDetails(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("opdoctors");
        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hosid=map.get("docid").toString();
                String docid=etdocid.getText().toString();
                String docname=etdocname.getText().toString();
                String docspec=etdocspec.getText().toString();

                DoctorModel doctorModel=new DoctorModel(hosid,docid,docname,docspec);
                
                userid=databaseReference.push().getKey();
                databaseReference.child(userid).setValue(doctorModel);
                addUserListener();
            }
        });
    }

    private void addUserListener() {

        final ProgressDialog progressDialog = new ProgressDialog(AddDoctor.this);
        progressDialog.setTitle("connecting...");
        progressDialog.show();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DoctorModel user = dataSnapshot.getValue(DoctorModel.class);

                // Check for null
                if (user == null) {
                    Log.e("data", "User data is null!");
                    ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                    progressDialog.hide();
                    return;
                }else {

                    Log.e("data", "User data is changed!" + user.getDocid() + ", " + user.getDocname());
                    ToastHelper.toastMsg(getApplicationContext(), "Doctor added successfully");
                    startActivity(new Intent(getApplicationContext(), AdminSuccess.class));
                    progressDialog.hide();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("data","user data is cancelled");
                ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                progressDialog.hide();
            }
        });
    }
}
