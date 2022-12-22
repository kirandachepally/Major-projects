package com.us.onlineop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;
import com.us.onlineop.Models.UserModel;

public class UserRegister extends AppCompatActivity {

    EditText etemail,etpassword,etname,etmobile;
    DatabaseReference mdatabaseref;
    String userid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        etemail=findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpassword);
        etname=findViewById(R.id.etname);
        mdatabaseref= FirebaseDatabase.getInstance().getReference("opregusers");
        etmobile=findViewById(R.id.etmobile);
        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etemail.getText().toString();
                String passwword=etpassword.getText().toString();
                String name=etname.getText().toString();
                String mobile=etmobile.getText().toString();
if(validate(email,passwword,name,mobile)) {
    UserModel userModel = new UserModel(email, name, passwword, mobile);
    userid = mdatabaseref.push().getKey();
    mdatabaseref.child(userid).setValue(userModel);
    userChangeListener();
}
            }
        });
    }

    private void userChangeListener() {

        final ProgressDialog progressDialog = new ProgressDialog(UserRegister.this);
        progressDialog.setTitle("connecting...");
        progressDialog.show();
        mdatabaseref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);

                // Check for null
                if (user == null) {
                    Log.e("data", "User data is null!");
                    ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                    progressDialog.hide();
                    return;
                }else {

                    Log.e("data", "User data is changed!" + user.getEmailid() + ", " + user.getMobile());
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

    public boolean validate(String email, String name, String password, String mobile) {

        if(email.isEmpty()  || !email.contains("@") || !email.contains(".com")){
            Toast.makeText(getApplicationContext(),"email format is wrong",Toast.LENGTH_SHORT).show();
            return false;
        }else if(name.isEmpty() || name==null){
            Toast.makeText(getApplicationContext(),"name is empty",Toast.LENGTH_SHORT).show();
            return  false;
        }else if(password.isEmpty() || password==null ){
            Toast.makeText(getApplicationContext(),"password should 6 characters",Toast.LENGTH_SHORT).show();
            return false;

        }else if(mobile.isEmpty() || mobile==null || mobile.length()<10){
            Toast.makeText(getApplicationContext(),"Mobile number should be 10 characteres",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }


}
