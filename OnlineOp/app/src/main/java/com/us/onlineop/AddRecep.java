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
import com.us.onlineop.Models.RecepModel;
import com.us.onlineop.Models.Store;

import java.util.HashMap;

public class AddRecep extends AppCompatActivity {

    EditText ethosid,etrecepname,etreceid;
    String userid;
    DatabaseReference mdatabaseref;
    HashMap map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recep);
        ethosid=findViewById(R.id.ethosid);
        etreceid=findViewById(R.id.btrecid);
        etrecepname=findViewById(R.id.btrecname);
        map= Store.getUserDetails(this);
        mdatabaseref= FirebaseDatabase.getInstance().getReference("oprecep");
        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hosid=map.get("docid").toString();
                String recepid=etreceid.getText().toString();
                String recname=etrecepname.getText().toString();

                userid=mdatabaseref.push().getKey();
                RecepModel recepModel=new RecepModel(hosid,recepid,recname);
                mdatabaseref.child(userid).setValue(recepModel);
                userChangeListener();
            }
        });
    }

    private void userChangeListener() {

        final ProgressDialog progressDialog = new ProgressDialog(AddRecep.this);
        progressDialog.setTitle("connecting...");
        progressDialog.show();
        mdatabaseref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               RecepModel user = dataSnapshot.getValue(RecepModel.class);

                // Check for null
                if (user == null) {
                    Log.e("data", "User data is null!");
                    ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                    progressDialog.hide();
                    return;
                }else {

                    Log.e("data", "User data is changed!" + user.getHospid() + ", " + user.getRecepname());
                    ToastHelper.toastMsg(getApplicationContext(), "Added successfully");
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
