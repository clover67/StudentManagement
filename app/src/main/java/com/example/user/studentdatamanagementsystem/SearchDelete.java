package com.example.user.studentdatamanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SearchDelete extends AppCompatActivity {

    EditText etSearchDelete;

    // User name (make variable public to access from outside)
    public static final String KEY_SID = "sID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_delete);

        etSearchDelete = (EditText) findViewById(R.id.etSearchDelete);
        final Button btnSearch = (Button) findViewById(R.id.btnSearchDelete);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //convert to string
                final String sID = etSearchDelete.getText().toString();

                if (validate1()) {
                    //optional
                    final ProgressDialog progressDialog = new ProgressDialog(SearchDelete.this);
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

                                    // Storing name in pref
                                    editor.putString(KEY_SID, sID);

                                    // commit changes
                                    editor.commit();

                                    Context context = getApplicationContext();
                                    CharSequence text = "Student Info found!";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    //go to another activity
                                    Intent intent = new Intent(SearchDelete.this, DeleteStudent.class);

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

                                    SearchDelete.this.startActivity(intent);
                                } else {
                                    //optional
                                    progressDialog.dismiss();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchDelete.this);
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
                    RequestQueue queue = Volley.newRequestQueue(SearchDelete.this);
                    queue.add(searchPageRequest);

                }

            }

        });
    }

    public boolean validate1() {
        String sID = etSearchDelete.getText().toString();

        boolean valid = true;

        String idPattern = "^([B]+[0-9+]+)$";


        if (!sID.matches(idPattern)) {
            valid = false;
            etSearchDelete.setError("Format: B031410111");
        } else {
            etSearchDelete.setError(null);
        }


        if (sID.isEmpty()) {
            valid = false;
            etSearchDelete.setError("Please insert a Student ID.");
        }
        return valid;
    }

}