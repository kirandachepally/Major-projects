package com.us.onlineop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.us.onlineop.Models.Opusers;

import java.util.List;

class OpAdapter  extends BaseAdapter {
    Context context;
    List<Opusers> opusers;
    DatabaseReference mdatabseref;

    public OpAdapter(Get_Op_Doctor get_op_doctor, List<Opusers> opusersList) {
    this.context=get_op_doctor;
    this.opusers=opusersList;
    mdatabseref= FirebaseDatabase.getInstance().getReference("opusers");
    }

    @Override
    public int getCount() {
        return opusers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.opuser_row,null);

        TextView tvpatid=convertView.findViewById(R.id.ptid);
        TextView tvpatname=convertView.findViewById(R.id.ptname);
        TextView tvpatsymp=convertView.findViewById(R.id.ptsymptoms);
        TextView tvpatage=convertView.findViewById(R.id.ptage);
        TextView tvpatgender=convertView.findViewById(R.id.ptgender);
        TextView tvpatstatus=convertView.findViewById(R.id.ptstatus);
        Button btsubmit=convertView.findViewById(R.id.btsubmit);
        final RadioGroup radioGroup=convertView.findViewById(R.id.rgstatus);
        final RadioButton[] radioSexButton = new RadioButton[1];
         final Opusers opuser= opusers.get(position);
         tvpatid.setText("Patient id:\t\t"+opuser.getOpid());
        tvpatname.setText("Patient name:\t\t"+opuser.getName());
        tvpatsymp.setText("Symtoms:\t\t"+opuser.getSymptoms());
        Log.e("sump",opuser.getSymptoms());
        tvpatage.setText("Age :\t\t"+opuser.getAge());tvpatgender.setText("Gender:\t\t"+opuser.getGender());
        tvpatstatus.setText("Status:\t\t"+opuser.getStatus());
        final View finalConvertView = convertView;
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioSexButton[0] = finalConvertView.findViewById(selectedId);

opuser.setStatus(radioSexButton[0].getText().toString());

    mdatabseref.child(opuser.getUserkey()).setValue(opuser);



Toast.makeText(context,"status updated",Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,DoctorSuccess.class));

            }
        });

        return convertView;
    }
}
