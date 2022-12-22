package com.us.onlineop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DoctorSuccess extends AppCompatActivity {

    Button btop;
    Button btavailability;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sucess);
        btop=findViewById(R.id.getOp);
        btavailability=findViewById(R.id.availabilty);
        btop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Get_Op_Doctor.class));
            }
        });

    btavailability.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Availability.class));
        }
    });
    }

}
