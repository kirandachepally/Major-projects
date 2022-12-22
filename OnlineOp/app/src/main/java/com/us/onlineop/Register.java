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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;
import com.us.onlineop.Models.RecepModel;
import com.us.onlineop.Models.RegisterModel;

public class Register extends AppCompatActivity {

    EditText etemail,etname,etpassword,etid;
    Spinner sptype;
    Button btsubmit;
    DatabaseReference databaseReference;
    String userid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etemail=findViewById(R.id.etemai);
        etname=findViewById(R.id.etname);
        etpassword=findViewById(R.id.etpassword);
        etid=findViewById(R.id.etid);
        sptype=findViewById(R.id.sptype);
        btsubmit=findViewById(R.id.btreg);
        databaseReference= FirebaseDatabase.getInstance().getReference("opregister");
        String s[]=new String[]{"doctor","receptionist"};

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,s);
        sptype.setAdapter(arrayAdapter);

        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=etemail.getText().toString();
                final String name=etname.getText().toString();
                final String password=etpassword.getText().toString();
                final String id=etid.getText().toString();
                final String type=sptype.getSelectedItem().toString();
                if(validate(email,name,password,id,type)){

                    if(type.equalsIgnoreCase("doctor")){
                        DatabaseReference mdatabase=FirebaseDatabase.getInstance().getReference("opdoctors");
                        Query query=mdatabase.orderByChild("docid").equalTo(id);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    DoctorModel doctor  = dataSnapshot.getChildren().iterator().next()
                                            .getValue(DoctorModel.class);
                                    userid=databaseReference.push().getKey();

                                    RegisterModel registerModel=new RegisterModel(email,password,name,id,type,doctor.getHospid());
                                    registerModel.setAvailability("1");
                                    registerModel.setTommorowavailability("1");

                                    databaseReference.child(userid).setValue(registerModel);
                                    addUserListener();

                                }else{
                                    Toast.makeText(getApplicationContext(),"doctor id not foud",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }else{

                        DatabaseReference mdatabase=FirebaseDatabase.getInstance().getReference("oprecep");
                        Query query=mdatabase.orderByChild("recepid").equalTo(id);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    RecepModel recep  = dataSnapshot.getChildren().iterator().next()
                                            .getValue(RecepModel.class);
                                    userid=databaseReference.push().getKey();

                                    RegisterModel registerModel=new RegisterModel(email,password,name,id,type,recep.getHospid());
                                    registerModel.setAvailability("1");
                                    registerModel.setTommorowavailability("1");

                                    databaseReference.child(userid).setValue(registerModel);
                                    addUserListener();

                                }else{
                                    Toast.makeText(getApplicationContext(),"receptionist id not foud",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }





                }else{

                }


            }
        });
    }

    public boolean validate(String email, String name, String password, String id, String type) {

        if(email.isEmpty()  || !email.contains("@") || !email.contains(".com")){
            Toast.makeText(getApplicationContext(),"email format is wrong",Toast.LENGTH_SHORT).show();
            return false;
        }else if(name.isEmpty() || name==null){
            Toast.makeText(getApplicationContext(),"name is empty",Toast.LENGTH_SHORT).show();
            return  false;
        }else if(password.isEmpty() || password==null || password.length()<6){
            Toast.makeText(getApplicationContext(),"password should 6 characters",Toast.LENGTH_SHORT).show();
            return false;
        }else if(id.isEmpty() || id==null){
            Toast.makeText(getApplicationContext(),"id is empty",Toast.LENGTH_SHORT).show();
            return false;
        }else if(type.isEmpty() || type==null){
            Toast.makeText(getApplicationContext(),"type is not selected",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

    private void addUserListener() {


        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("connecting...");
        progressDialog.show();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RegisterModel user = dataSnapshot.getValue(RegisterModel.class);

                // Check for null
                if (user == null) {
                    Log.e("data", "User data is null!");
                    ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                    progressDialog.hide();
                    return;
                }else {

                  //  Log.e("data", "User data is changed!" +  + ", " + user.getRecepname());
                    ToastHelper.toastMsg(getApplicationContext(), "Registration success");
                    startActivity(new Intent(getApplicationContext(), Hospital.class));
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
