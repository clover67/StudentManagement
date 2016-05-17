package com.example.user.studentdatamanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        Intent intent = getIntent();
        String sID = intent.getStringExtra("sID");
        String sName = intent.getStringExtra("sName");
        String sIC = intent.getStringExtra("sIC");
        String sGender = intent.getStringExtra("sGender");
        String sFaculty = intent.getStringExtra("sFaculty");
        String sRace = intent.getStringExtra("sRace");
        String sDOB = intent.getStringExtra("sDOB");
        String sTelNo = intent.getStringExtra("sTelNo");
        String sEmail = intent.getStringExtra("sEmail");


        TextView tvsID = (TextView) findViewById(R.id.tvsID);
        TextView tvsName = (TextView) findViewById(R.id.tvsName);
        TextView tvsIC = (TextView) findViewById(R.id.tvsIC);
        TextView tvsGender = (TextView) findViewById(R.id.tvsGender);
        TextView tvsFaculty = (TextView) findViewById(R.id.tvsFaculty);
        TextView tvsRace = (TextView) findViewById(R.id.tvsRace);
        TextView tvsDOB = (TextView) findViewById(R.id.tvsDOB);
        TextView tvsTel = (TextView) findViewById(R.id.tvsTel);
        TextView tvsEmail = (TextView) findViewById(R.id.tvsEmail);

        tvsID.setText(sID);
        tvsName.setText(sName);
        tvsIC.setText(sIC);
        tvsGender.setText(sGender);
        tvsFaculty.setText(sFaculty);
        tvsRace.setText(sRace);
        tvsDOB.setText(sDOB);
        tvsTel.setText(sTelNo);
        tvsEmail.setText(sEmail);

        final Button btnBack = (Button) findViewById(R.id.btnCancel);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataList.this, Display.class);
                DataList.this.startActivity(intent);
            }
        });
    }
}