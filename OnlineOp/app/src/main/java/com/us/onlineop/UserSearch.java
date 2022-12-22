package com.us.onlineop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSearch extends AppCompatActivity {

    EditText editText;
DatabaseReference mdatabaseref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        editText=findViewById(R.id.etstate);
        final EditText editText1=findViewById(R.id.etcity);
        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // mdatabaseref= FirebaseDatabase.getInstance().getReference("ophospitals");
                Intent i=new Intent(getApplicationContext(),Hospitalslist.class);
                i.putExtra("state",editText.getText().toString());
                i.putExtra("city",editText1.getText().toString());
                startActivity(i);

               // startActivity(new Intent(getApplicationContext(),Hospitalslist.class).putExtra("state",editText.getText().toString()));
            }
        });
    }
}
