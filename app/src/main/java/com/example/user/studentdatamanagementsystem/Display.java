package com.example.user.studentdatamanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by USER on 26/4/2016.
 */
public class Display extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        TextView tv = (TextView) findViewById(R.id.TVusername);
        tv.setText("Welcome, "+ username);

        final Button bAdduser = (Button) findViewById(R.id.button_adduser);
        final Button bManage = (Button) findViewById(R.id.button_manage);
        final Button bSearch = (Button) findViewById(R.id.button_search);

        bAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display.this, add_user.class);
                Display.this.startActivity(intent);
            }
         });


    }
}
