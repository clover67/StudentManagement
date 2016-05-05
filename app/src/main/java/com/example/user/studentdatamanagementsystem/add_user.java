package com.example.user.studentdatamanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class add_user extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        final EditText etName = (EditText) findViewById(R.id.txtName);
        final EditText etUsername = (EditText) findViewById(R.id.txtUsername);
        final EditText etPassword = (EditText) findViewById(R.id.txtPassword);
        final EditText etEmail = (EditText) findViewById(R.id.txtEmail);
        final RadioGroup rgGender = (RadioGroup) findViewById(R.id.rgGender);
        final Button bBack = (Button) findViewById(R.id.button_back);
        final Button button_signup = (Button) findViewById(R.id.button_signup);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_user.this, Display.class);
                add_user.this.startActivity(intent);
            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //optional
                final ProgressDialog progressDialog = new ProgressDialog(add_user.this);
                progressDialog.setMessage("Signing up...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //optional

                final RadioButton selectRadio = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());

                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String gender = selectRadio.getText().toString();
                final String email = etEmail.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                progressDialog.dismiss();
                                //go to another activity
                                Intent intent = new Intent(add_user.this, Display.class);
                                //optional
                                add_user.this.startActivity(intent);
                            } else {
                                //optional
                                progressDialog.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(add_user.this);
                                builder.setMessage("Signup failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                SignUpRequest signUpRequest = new SignUpRequest(name, username, password, gender, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(add_user.this);
                queue.add(signUpRequest);
            }
        });
    }
}
