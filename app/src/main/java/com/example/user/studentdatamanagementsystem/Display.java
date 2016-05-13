package com.example.user.studentdatamanagementsystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by USER on 26/4/2016.
 */
public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

       final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("username");

        // user name
        String username = pref.getString("username", null);

        TextView tv = (TextView) findViewById(R.id.TVusername);
        tv.setText("Welcome, "+ username);

        final Button bAdduser = (Button) findViewById(R.id.button_adduser);
        final Button bManage = (Button) findViewById(R.id.button_manage);
        final Button bSearch = (Button) findViewById(R.id.button_search);
        final Button bLogOut = (Button) findViewById(R.id.button_logout);

        bAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display.this, add_user.class);
                Display.this.startActivity(intent);
            }
        });

        bManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display.this, ManagePage.class);
                Display.this.startActivity(intent);
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display.this, SearchPage.class);
                Display.this.startActivity(intent);
            }
        });

        bLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent  = new Intent(Display.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                AlertDialog.Builder builder = new AlertDialog.Builder(Display.this);
                builder.setMessage("Are you sure want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(intent);
                            }

                        })
                        .setNegativeButton("No", new Dialog.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                });
                builder.create()
                        .show();


            }
        });

    }
}
