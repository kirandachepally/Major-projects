package com.us.onlineop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UserSuccess extends AppCompatActivity {

    Button btgetop,btdashboard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_success);
    btdashboard=findViewById(R.id.btdashboard);
    btgetop=findViewById(R.id.btgetop);

    btgetop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),UserSearch.class));
        }
    });

    btdashboard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),DashBoard.class));
        }
    });


    }
}
