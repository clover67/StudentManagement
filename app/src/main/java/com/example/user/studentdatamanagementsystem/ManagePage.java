package com.example.user.studentdatamanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManagePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_page);



        final Button bAddStudent = (Button) findViewById(R.id.btnAdd);
        final Button bUpdateStudent = (Button) findViewById(R.id.btnUpdate);
        final Button bDeleteStudent = (Button) findViewById(R.id.btnDelete);
        final Button bMBack = (Button) findViewById(R.id.btnBack);

        bAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagePage.this, AddStudent.class);
                ManagePage.this.startActivity(intent);
            }
        });

        bUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagePage.this, SearchStudent.class);
                ManagePage.this.startActivity(intent);
            }
        });

        bDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagePage.this, SearchDelete.class);
                ManagePage.this.startActivity(intent);
            }
        });

        bMBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagePage.this, Display.class);
                ManagePage.this.startActivity(intent);
            }
        });
    }
}
