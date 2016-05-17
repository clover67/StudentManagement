package com.example.user.studentdatamanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchStudent extends AppCompatActivity {

    EditText etSearchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        etSearchID = (EditText) findViewById(R.id.etSearchID);
        final Button btnSearch = (Button) findViewById(R.id.btnSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //convert to string
                final String sID = etSearchID.getText().toString();

                if (validate()) {
                    //optional
                    final ProgressDialog progressDialog = new ProgressDialog(SearchStudent.this);
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
                                    String sID = jsonResponse.getString("sID");
                                    String sName = jsonResponse.getString("sName");
                                    String sIC = jsonResponse.getString("sIC");
                                    String sGender = jsonResponse.getString("sGender");
                                    String sFaculty = jsonResponse.getString("sFaculty");
                                    String sRace = jsonResponse.getString("sRace");
                                    String sDOB = jsonResponse.getString("sDOB");
                                    String sTelNo = jsonResponse.getString("sTelNo");
                                    String sEmail = jsonResponse.getString("sEmail");


                                    Context context = getApplicationContext();
                                    CharSequence text = "Student Info found!";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    //go to another activity
                                    Intent intent = new Intent(SearchStudent.this, UpdateStudent.class);

                                    //pass some data using putExtra()
                                    intent.putExtra("sID", sID);
                                    intent.putExtra("sName", sName);
                                    intent.putExtra("sIC", sIC);
                                    intent.putExtra("sGender", sGender);
                                    intent.putExtra("sFaculty", sFaculty);
                                    intent.putExtra("sRace", sRace);
                                    intent.putExtra("sDOB", sDOB);
                                    intent.putExtra("sTelNo", sTelNo);
                                    intent.putExtra("sEmail", sEmail);

                                    //optional
                                    progressDialog.dismiss();

                                    SearchStudent.this.startActivity(intent);
                                } else {
                                    //optional
                                    progressDialog.dismiss();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchStudent.this);
                                    builder.setMessage("Invalid Student ID!")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    };

                    SearchPageRequest searchPageRequest = new SearchPageRequest(sID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SearchStudent.this);
                    queue.add(searchPageRequest);

                }

            }

        });

    }

    public boolean validate() {
        String sID = etSearchID.getText().toString();

        String idPattern = "^([B]+[0-9+]+)$";

        boolean valid = true;
        if (!sID.matches(idPattern)) {
            valid = false;
            etSearchID.setError("Format: B031410111");
        } else {
            etSearchID.setError(null);
        }


        if (sID.isEmpty()) {
            valid = false;
            etSearchID.setError("Please insert a Student ID.");
        }
        return valid;
    }

}
