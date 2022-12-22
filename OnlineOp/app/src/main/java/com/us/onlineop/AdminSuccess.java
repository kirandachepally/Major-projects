package com.us.onlineop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

 public class AdminSuccess extends AppCompatActivity implements View.OnClickListener {

    Button btadddoc,btaddrecep,btviewdoc,btviewrecep;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsuccess);
        btadddoc=findViewById(R.id.btadddoctor);
        btaddrecep=findViewById(R.id.btadddrecep);
        btviewdoc=findViewById(R.id.btviewdoctors);
        btviewrecep=findViewById(R.id.btviewrecep);

        btadddoc.setOnClickListener(this);
        btaddrecep.setOnClickListener(this);
        btviewdoc.setOnClickListener(this);
        btviewrecep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btadddoctor:{
                startActivity(new Intent(getApplicationContext(),AddDoctor.class));
                break;
            }case R.id.btadddrecep:{
                startActivity(new Intent(getApplicationContext(),AddRecep.class));
                break;
            }case R.id.btviewdoctors:{
                startActivity(new Intent(getApplicationContext(),ViewDoctors.class));
                break;
            }case R.id.btviewrecep:{
                startActivity(new Intent(getApplicationContext(),ViewRecepe.class));
                break;
            }






        }

    }
}
