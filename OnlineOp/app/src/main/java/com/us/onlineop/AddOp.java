package com.us.onlineop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.us.onlineop.Models.DoctorModel;
import com.us.onlineop.Models.Opusers;
import com.us.onlineop.Models.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddOp extends AppCompatActivity {

    EditText etname,etsymptoms,etgender,etage,etdocid;
    Spinner gender;
    DatabaseReference mdatabaseref;
    String docid;
    String userid;
    List<Opusers> opusersList;
    ListView lvlist;
    HashMap map;
List<DoctorModel> doctorModelList;
    ArrayList arrayList;
    Opusers op;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_op);
        etname=findViewById(R.id.etname);
        etdocid=findViewById(R.id.etdocid);
        lvlist=findViewById(R.id.lvlist);
        opusersList=new ArrayList<>();
        map= Store.getUserDetails(this);
etsymptoms=findViewById(R.id.etsymptoms);
        arrayList=new ArrayList();
doctorModelList=new ArrayList<>();
        mdatabaseref=FirebaseDatabase.getInstance().getReference("opdoctors");
        Query query=mdatabaseref.orderByChild("hospid").equalTo(map.get("docid").toString());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    DoctorModel doctorModel=dataSnapshot1.getValue(DoctorModel.class);
                   doctorModelList.add(doctorModel);
                }

                DoctorDisplay arrayAdapter=new DoctorDisplay(AddOp.this,doctorModelList);
                lvlist.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabaseref= FirebaseDatabase.getInstance().getReference("opusers");
        Bundle bundle=getIntent().getExtras();
      //  docid=bundle.getString("docid");
        etage=findViewById(R.id.age);
        gender=findViewById(R.id.spgender);
        String s[]={"male","female"};
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,s);
        gender.setAdapter(arrayAdapter);
        findViewById(R.id.btsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=etname.getText().toString();
                final String symptoms=etsymptoms.getText().toString();
                final String gender1=gender.getSelectedItem().toString();
                final String age=etage.getText().toString();
                final String docid=etdocid.getText().toString();
                final String[] userid = new String[1];
                final int[] i = new int[1];
                if(validate(name,symptoms,gender1,age,docid)){
                   // Opusers opusers=new Opusers();

                    final Opusers opusers=new Opusers();
                    mdatabaseref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                userid[0] =dataSnapshot1.getKey();
                                Opusers opusers1=dataSnapshot1.getValue(Opusers.class);
                                opusersList.add(opusers1);
                                Log.e("opusers",String.valueOf(opusersList.size()));
                                 i[0] =opusersList.size()+1;
                            }

                          if(i[0]==0){
                              i[0]=1;
                          }
                            opusers.setOpid(String.valueOf(i[0]));
                            Log.e("ivalue is",String.valueOf(i[0]));
                            opusers.setName(name);opusers.setSymptoms(symptoms);opusers.setGender(gender1);
                            opusers.setAge(age);opusers.setDocid(docid);opusers.setStatus("Waiting");
                            opusers.setUserkey(userid[0]);

                            userid[0] =mdatabaseref.push().getKey();
                            //   opusers.setUserkey(userid);
                            opusers.setUserkey(userid[0]);
                            mdatabaseref.child(userid[0]).setValue(opusers);
                            addOpListener();
                            Toast.makeText(getApplicationContext(),"addedd op",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Receptionist.class));
                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                }



            }
        });
    }


    private void addOpListener() {

        final ProgressDialog progressDialog = new ProgressDialog(AddOp.this);
        progressDialog.setTitle("connecting...");
        progressDialog.show();
        final List<Opusers> opusersList=new ArrayList<>();

        mdatabaseref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Opusers user = dataSnapshot.getValue(Opusers.class);

                // Check for null
                if (user == null) {
                    Log.e("data", "User data is null!");
                    ToastHelper.toastMsg(getApplicationContext(),"something went wrong");
                    progressDialog.hide();
                    return;
                }else {
                    Query query= mdatabaseref.orderByChild("docid").equalTo(user.getDocid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                op=dataSnapshot1.getValue(Opusers.class);
                                opusersList.add(op);


                            }
                            int i=opusersList.size();
                            op.setOpid(String.valueOf(i));
                            mdatabaseref.child(userid).setValue(op);
                            progressDialog.hide();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            ToastHelper.toastMsg(getApplicationContext(),databaseError.toString());
                        }
                    });


                    // ToastHelper.toastMsg(getApplicationContext(), "Registration success");
                    //  startActivity(new Intent(getApplicationContext(), UserSuccess.class));
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

    public boolean validate(String name, String symptoms, String gender1, String age, String docid) {

        if(name.isEmpty() || name==null){
            Toast.makeText(getApplicationContext(),"name cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }else if(symptoms.isEmpty() || symptoms==null){
            Toast.makeText(getApplicationContext(),"symptoms cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }else if(gender1.isEmpty() || gender1==null){
            Toast.makeText(getApplicationContext(),"select gender",Toast.LENGTH_SHORT).show();
            return false;
        }else if(age.isEmpty() || age==null){
            Toast.makeText(getApplicationContext(),"age filed is empty",Toast.LENGTH_SHORT).show();
            return false;
        }else if(docid.isEmpty() || docid==null){
            Toast.makeText(getApplicationContext(),"doc id is empty",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return  true;
        }


    }
}
