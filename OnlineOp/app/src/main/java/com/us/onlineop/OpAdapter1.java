package com.us.onlineop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.us.onlineop.Models.Opusers;

import java.util.List;

class OpAdapter1  extends BaseAdapter {
    Context context;
    List<Opusers> opusers;
    DatabaseReference mdatabseref;

    public OpAdapter1(DashBoard get_op_doctor, List<Opusers> opusersList) {
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
        convertView= LayoutInflater.from(context).inflate(R.layout.opuser1,null);

        TextView tvpatid=convertView.findViewById(R.id.ptid);
        TextView tvpatname=convertView.findViewById(R.id.ptname);
        TextView tvpatsymp=convertView.findViewById(R.id.ptsymptoms);
        TextView tvpatage=convertView.findViewById(R.id.ptage);
        TextView tvpatgender=convertView.findViewById(R.id.ptgender);
        TextView tvpatstatus=convertView.findViewById(R.id.ptstatus);
        Button btsubmit=convertView.findViewById(R.id.btsubmit);

        final Opusers opuser= opusers.get(position);
        tvpatid.setText("patient id:\t\t"+opuser.getOpid());
        tvpatname.setText("patient name:\t\t"+opuser.getName());
        tvpatsymp.setText("symptoms:\t\t"+opuser.getSymptoms());
        Log.e("sump",opuser.getSymptoms());
        tvpatage.setText("Age :\t\t"+opuser.getAge());tvpatgender.setText("Gender:"+opuser.getGender());
        tvpatstatus.setText("status:\t\t"+opuser.getStatus());
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  opuser.setStatus("visited");
                //mdatabseref.child(opuser.getUserkey()).setValue(opuser);

            }
        });
        return convertView;
    }
}
