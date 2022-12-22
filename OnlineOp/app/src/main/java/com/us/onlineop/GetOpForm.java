package com.us.onlineop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.Opusers;
import com.us.onlineop.Models.RegisterModel;
import com.us.onlineop.Models.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetOpForm extends AppCompatActivity {

    EditText etname,etsymptoms,etgender,etage;
    Spinner gender;
    DatabaseReference mdatabaseref;
    String docid;
    String userid;
    HashMap map;
    Opusers op;
    List<Opusers> opusersList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getop);
        etname=findViewById(R.id.etname);
        map= Store.getUserDetails(this);
etsymptoms=findViewById(R.id.etsymptoms);
        mdatabaseref= FirebaseDatabase.getInstance().getReference("opusers");
        Bundle bundle=getIntent().getExtras();
         docid=bundle.getString("docid");
        etage=findViewById(R.id.age);
        gender=findViewById(R.id.spgender);
        opusersList=new ArrayList<>();
        String s[]={"male","female"};
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,s);
        gender.setAdapter(arrayAdapter);
        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=etname.getText().toString();
                final String symptoms=etsymptoms.getText().toString();
                final String gender1=gender.getSelectedItem().toString();
                final String age=etage.getText().toString();
               if(validate(name,symptoms,gender1,age)){
                   final Opusers opusers=new Opusers();
                    mdatabaseref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                    Opusers opusers1=dataSnapshot1.getValue(Opusers.class);
                                    opusersList.add(opusers1);
                                    Log.e("opusers",String.valueOf(opusersList.size()));
                                }


                            int i=opusersList.size()+1;
                            opusers.setOpid(String.valueOf(i));
                            opusers.setName(name);opusers.setSymptoms(symptoms);opusers.setGender(gender1);
                            opusers.setAge(age);opusers.setDocid(docid);opusers.setStatus("waiting");
                            opusers.setOpuseridkey(map.get("docid").toString());
                            userid=mdatabaseref.push().getKey();
                            opusers.setUserkey(userid);
                            mdatabaseref.child(userid).setValue(opusers);
                            addOpListener();
                            Toast.makeText(getApplicationContext(),"addedd op",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),UserSuccess.class));
                            finish();
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

               }else{

               }



            }
        });
    }

    private void addOpListener() {

       final ProgressDialog progressDialog = new ProgressDialog(GetOpForm.this);
        progressDialog.setTitle("connecting...");
        progressDialog.show();
        final List<Opusers> opusersList=new ArrayList<>();

        mdatabaseref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Opusers user = dataSnapshot.getValue(Opusers.class);

                // Check for null
                if (user == null) {
                    Log.e("data", "User data is null!");
                    ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                    progressDialog.hide();
                    return;
                }else {
                    Query query= mdatabaseref.orderByChild("docid").equalTo(user.getDocid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                               op=dataSnapshot1.getValue(Opusers.class);
                                opusersList.add(op);


                            }
                            int i=opusersList.size();
                            op.setOpid(String.valueOf(i));
                            mdatabaseref.child(userid).setValue(op);
                            progressDialog.hide();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
ToastHelper.toastMsg(getApplicationContext(),databaseError.toString());
                        }
                    });


                   // ToastHelper.toastMsg(getApplicationContext(), "Registration success");
                  //  startActivity(new Intent(getApplicationContext(), UserSuccess.class));
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

    private boolean validate(String name, String symptoms, String gender1, String age) {
        if(name.isEmpty() || name==null){
            Toast.makeText(getApplicationContext(),"name cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }else if(symptoms.isEmpty() || symptoms==null){
            Toast.makeText(getApplicationContext(),"symptoms cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }else if(gender1.isEmpty() || gender1==null){
            Toast.makeText(getApplicationContext(),"select gender",Toast.LENGTH_SHORT).show();
            return false;
        }else if(age.isEmpty() || age==null){
            Toast.makeText(getApplicationContext(),"age filed is empty",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return  true;
        }
    }
}
