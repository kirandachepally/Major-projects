package com.us.onlineop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Hospital extends AppCompatActivity {

    Button btadmin,btdoc,btrecep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        btadmin=findViewById(R.id.btadmin);
        btdoc=findViewById(R.id.btdoctor);
        btrecep=findViewById(R.id.btrecep);

        btadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),Login.class).putExtra("user","admin"));

            }
        });

        btdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class).putExtra("user","doctor"));
            }
        });
        btrecep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class).putExtra("user","recep"));

            }
        });
    }
}
