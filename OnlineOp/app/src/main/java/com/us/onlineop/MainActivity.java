package com.us.onlineop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bthospital,btuser,btreghospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    bthospital=findViewById(R.id.btreghospital);
    btreghospital=findViewById(R.id.bthospital);
    btuser=findViewById(R.id.btuser);

    bthospital.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Hospital.class));
        }
    });

    btuser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),User.class));

        }
    });

    btreghospital.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),RegisterHospital.class));
        }
    });

    }
}
