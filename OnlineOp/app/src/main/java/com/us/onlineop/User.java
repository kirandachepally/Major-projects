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

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.RegisterModel;
import com.us.onlineop.Models.Store;
import com.us.onlineop.Models.UserModel;

public class User extends AppCompatActivity {

    EditText etusername,etpassword;
    DatabaseReference mdatabaseref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        etusername=findViewById(R.id.etusrname);
        etpassword=findViewById(R.id.etpassword);

        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etusername.getText().toString();
                final String password=etpassword.getText().toString();
               if(validate(username,password)) {
                   mdatabaseref = FirebaseDatabase.getInstance().getReference("opregusers");
                   Query query = mdatabaseref.orderByChild("emailid").equalTo(username);
                   query.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists()) {
                               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                   UserModel registerModel = dataSnapshot1.getValue(UserModel.class);

                                   if (registerModel.getPassword().equalsIgnoreCase(password)) {

                                       startActivity(new Intent(getApplicationContext(), UserSuccess.class));
                                       Store.userDetails(getApplicationContext(), dataSnapshot1.getKey());
                                   } else {
                                       Toast.makeText(User.this, "password wrong", Toast.LENGTH_SHORT).show();
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


               }

            }
        });

        findViewById(R.id.btreg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserRegister.class));
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
