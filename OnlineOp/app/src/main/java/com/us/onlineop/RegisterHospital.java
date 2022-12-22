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
import com.us.onlineop.Models.HosptialModel;
import com.us.onlineop.Models.RegisterModel;

public class RegisterHospital extends AppCompatActivity {

    EditText ethopsitalname,etemail,etpssword,etstate,etcity,etstreet,etmobile,ethospitaltype;
    DatabaseReference mdatabaseref;
    String userid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_hospital);
        ethopsitalname=findViewById(R.id.ethospitalname);
        etemail=findViewById(R.id.etemail);
        etpssword=findViewById(R.id.etpassword);
        final EditText etrepassword=findViewById(R.id.etrepassword);
        etstate=findViewById(R.id.etstate);
        etcity=findViewById(R.id.etcity);
        etstreet=findViewById(R.id.etstreet);
        etmobile=findViewById(R.id.etmobile);
        ethospitaltype=findViewById(R.id.ethospitaltype);
        mdatabaseref= FirebaseDatabase.getInstance().getReference("ophospitals");

        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hospitalname=ethopsitalname.getText().toString();
                String email=etemail.getText().toString();
                String password=etpssword.getText().toString();
                String repassword=etrepassword.getText().toString();
                String state=etstate.getText().toString();
                String city=etcity.getText().toString();
                String street=etstreet.getText().toString();
                String mobile=etmobile.getText().toString();
                String hospitaltype=ethospitaltype.getText().toString();
if(validate(hospitalname,email,password,repassword,state,city,street,mobile,hospitaltype)){
    HosptialModel hosptialModel=new HosptialModel(hospitalname,email,password,hospitaltype,state,city,street,mobile);
    userid=mdatabaseref.push().getKey();
    mdatabaseref.child(userid).setValue(hosptialModel);
    addHospitalListener();
}

            }
        });

    }

    private boolean validate(String hospitalname, String email, String password,String repassword, String state, String city, String street, String mobile, String hospitaltype) {
        if(hospitalname.isEmpty() || hospitalname==null){
            ToastHelper.toastMsg(getApplicationContext(),"hospital name cannot be empty");
            return false;
        }else if(email.isEmpty() || email==null || !email.contains(".com") || !email.contains("@")){
            ToastHelper.toastMsg(getApplicationContext(),"email id is not in correct format");
            return false;
        }else if(password.isEmpty() || password==null || password.length()<6){
            ToastHelper.toastMsg(getApplicationContext(),"password is less than 6 characters");
            return false;
        }else if(!password.equals(repassword)){
            ToastHelper.toastMsg(getApplicationContext(),"password and repassword are not same");
            return false;
        }
        else if(state.isEmpty() || state==null  ){
            ToastHelper.toastMsg(getApplicationContext(),"State filed cannot be empty");
            return false;
        }
        else if(city.isEmpty() || city==null ){
            ToastHelper.toastMsg(getApplicationContext(),"city field cannot be empty");
            return false;
        }
        else if(street.isEmpty() || street==null){
            ToastHelper.toastMsg(getApplicationContext(),"street cannot be empty");
            return false;
        } else if(mobile.isEmpty() || mobile==null || mobile.length()<10){
            ToastHelper.toastMsg(getApplicationContext(),"mobile should be 10 characters");
            return false;
        }else if(hospitaltype.isEmpty() || hospitaltype==null){
            ToastHelper.toastMsg(getApplicationContext(),"hospital type cannot be empty");
            return false;
        }else{
            return  true;
        }




    }

    private void addHospitalListener() {

        final ProgressDialog progressDialog = new ProgressDialog(RegisterHospital.this);
        progressDialog.setTitle("connecting...");
        progressDialog.show();
        mdatabaseref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HosptialModel user = dataSnapshot.getValue(HosptialModel.class);

                // Check for null
                if (user == null) {
                    Log.e("data", "User data is null!");
                    ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                    progressDialog.hide();
                    return;
                }else {

                    //  Log.e("data", "User data is changed!" +  + ", " + user.getRecepname());
                    ToastHelper.toastMsg(getApplicationContext(), "Registration success");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
