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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
     EditText editText_password;
     EditText editText_username;

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get value from interface
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_username = (EditText) findViewById(R.id.editText_username);
        final Button bLogin = (Button) findViewById(R.id.buttonLogin);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();



        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convert to string
                final String username = editText_username.getText().toString();
                final String password = editText_password.getText().toString();

                if(validate()){
                    //optional
                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Logging in...");
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
                                    String username = jsonResponse.getString("username");
                                    String password = jsonResponse.getString("password");
                                    String gender = jsonResponse.getString("gender");
                                    String email = jsonResponse.getString("email");


                                    // Storing name in pref
                                    editor.putString(KEY_NAME, username);

                                    // commit changes
                                    editor.commit();

                                    Context context = getApplicationContext();
                                    CharSequence text = "Login Successfully!";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    //go to another activity
                                    Intent intent = new Intent(MainActivity.this, Display.class);

                                    //pass some data using putExtra()
                                    intent.putExtra("username", username);

//                                intentToNav.putExtra("name", name);
//                                intentToNav.putExtra("username", username);
//                                intentToNav.putExtra("age", age);
//                                intentToNav.putExtra("email", email);
//                                intentToNav.putExtra("password", password);
//                                intentToNav.putExtra("userType", userType);

                                    //optional
                                    progressDialog.dismiss();

                                    MainActivity.this.startActivity(intent);
                                } else {
                                    //optional
                                    progressDialog.dismiss();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Login failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }
                        }

                    };
                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }
            }
        });

    }

    public boolean validate(){
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();
        boolean valid = true;
        if(username.isEmpty()){
            valid = false;
            editText_username.setError("username can't be empty");
        }
        if(password.isEmpty()){
            valid = false;
            editText_password.setError("password field can't be empty");
        }
        return valid;
    }
}
