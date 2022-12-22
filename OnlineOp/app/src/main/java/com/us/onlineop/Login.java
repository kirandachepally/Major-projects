package com.us.onlineop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.HosptialModel;
import com.us.onlineop.Models.RegisterModel;
import com.us.onlineop.Models.Store;

import java.util.concurrent.ThreadPoolExecutor;

public class Login extends AppCompatActivity {

    EditText etusername,etpassword;
    Button btsubmit,btreg;
    String user;
    DatabaseReference mdatabaseref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    etusername=findViewById(R.id.etusrname);
    etpassword=findViewById(R.id.etpassword);
    btsubmit=findViewById(R.id.btsubmit);
    btreg=findViewById(R.id.btreg);

    Bundle bundle=getIntent().getExtras();
    user=bundle.getString("user");
    btsubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username=etusername.getText().toString();
            final String password=etpassword.getText().toString();

            if(user.equalsIgnoreCase("admin")) {
                if (validate(username,password)) {
                    mdatabaseref= FirebaseDatabase.getInstance().getReference("ophospitals");
                Query query=mdatabaseref.orderByChild("email").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                HosptialModel registerModel =dataSnapshot1.getValue(HosptialModel.class);

                                if (registerModel.getPassword().equalsIgnoreCase(password)) {

                                    startActivity(new Intent(getApplicationContext(),AdminSuccess.class));
                                    Store.userDetails(getApplicationContext(),dataSnapshot1.getKey());
                                }else{
                                    Toast.makeText(Login.this, "password wrong", Toast.LENGTH_SHORT).show();
                                }


                            } }else{
                            ToastHelper.toastMsg(getApplicationContext(),"user not found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        ToastHelper.toastMsg(getApplicationContext(),databaseError.toString());
                    }
                });

               /* if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
                    startActivity(new Intent(getApplicationContext(), AdminSuccess.class));
                }
*/   }else{

                    }



            }else if(user.equalsIgnoreCase("doctor")){
                if(validate(username,password)) {
                    mdatabaseref = FirebaseDatabase.getInstance().getReference("opregister");
                    Query query = mdatabaseref.orderByChild("emailid").equalTo(username);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    RegisterModel registerModel = dataSnapshot1.getValue(RegisterModel.class);

                                    if (registerModel.getPassword().equalsIgnoreCase(password)) {

                                        startActivity(new Intent(getApplicationContext(), DoctorSuccess.class));
                                        Store.userDetails(getApplicationContext(), registerModel.getId());
                                    } else {
                                        Toast.makeText(Login.this, "password wrong", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            } else {
                                ToastHelper.toastMsg(getApplicationContext(), "user not found");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            ToastHelper.toastMsg(getApplicationContext(), databaseError.toString());
                        }
                    });

                }else{

                }

            }else if(user.equalsIgnoreCase("recep")){

                if(validate(username,password)){
                mdatabaseref= FirebaseDatabase.getInstance().getReference("opregister");
                Query query=mdatabaseref.orderByChild("emailid").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                RegisterModel registerModel =dataSnapshot1.getValue(RegisterModel.class);

                                if (registerModel.getPassword().equalsIgnoreCase(password)) {

                                  startActivity(new Intent(getApplicationContext(),Receptionist.class));
                                  Store.userDetails(getApplicationContext(),registerModel.getHospitalid());
                                }else{
                                    Toast.makeText(Login.this, "password wrong", Toast.LENGTH_SHORT).show();
                                }


                            } }else{
                            ToastHelper.toastMsg(getApplicationContext(),"user not found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        ToastHelper.toastMsg(getApplicationContext(),databaseError.toString());
                    }
                });


            }else{

                }
        }}
    });

    btreg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Register.class));
        }
    });

    }

    private boolean validate(String username,String password) {
        if(username.isEmpty() || username==null){
            Toast.makeText(getApplicationContext(),"username cannot be empty",Toast.LENGTH_SHORT).show();
            return  false;
        }else if(password.isEmpty() || password==null){
            Toast.makeText(getApplicationContext(),"password cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return  true;
        }

    }
}
