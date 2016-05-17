package com.example.user.studentdatamanagementsystem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);


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


        final TextView tvsID = (TextView) findViewById(R.id.tvsID);
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

        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        final Button btnDelete = (Button) findViewById(R.id.btnDelete);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteStudent.this, ManagePage.class);
                DeleteStudent.this.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sID = tvsID.getText().toString();


                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteStudent.this);
                builder.setMessage("Are you sure want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new Dialog.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //optional
                                final ProgressDialog progressDialog = new ProgressDialog(DeleteStudent.this);
                                progressDialog.setMessage("Loading...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                //optional

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            if (success) {
                                                //optional
                                                progressDialog.dismiss();

                                                final Intent intent = new Intent(DeleteStudent.this, ManagePage.class);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteStudent.this);
                                                builder.setMessage("Delete sucessfully")
                                                        .setPositiveButton("Okay", new Dialog.OnClickListener(){
                                                            @Override public void onClick(DialogInterface dialog, int which){
                                                                DeleteStudent.this.startActivity(intent);
                                                            }
                                                        });
                                                builder.create()
                                                        .show();



                                            } else {
                                                //optional
                                                progressDialog.dismiss();

                                                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteStudent.this);
                                                builder.setMessage("Delete failed!")
                                                        .setNegativeButton("Retry", null)
                                                        .create()
                                                        .show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                DeleteStudentRequest deleteStudentRequest = new DeleteStudentRequest(sID, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(DeleteStudent.this);
                                queue.add(deleteStudentRequest);

                            }
                        });
                builder.setNegativeButton("No", new Dialog.OnClickListener(){
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
