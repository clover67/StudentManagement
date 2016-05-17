package com.example.user.studentdatamanagementsystem;

        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.DialogInterface;
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
    EditText etName;
    EditText etUsername ;
    EditText etPassword ;
    EditText etEmail;
    RadioButton selectRadio;
    RadioGroup rgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        etName = (EditText) findViewById(R.id.txtName);
        etUsername = (EditText) findViewById(R.id.txtUsername);
        etPassword = (EditText) findViewById(R.id.txtPassword);
        etEmail = (EditText) findViewById(R.id.txtEmail);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);

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
                if (validate()) {

                    //optional
                    final ProgressDialog progressDialog = new ProgressDialog(add_user.this);
                    progressDialog.setMessage("Signing up...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    //optional

                    selectRadio = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
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
                                    final Intent intent = new Intent(add_user.this, Display.class);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(add_user.this);
                                    builder.setMessage("Sign Up successfully")
                                            .setPositiveButton("Okay", new Dialog.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    add_user.this.startActivity(intent);
                                                }

                                            });
                                    builder.create()
                                            .show();


                                } else {
                                    //optional
                                    progressDialog.dismiss();
                                    String errorMessage = jsonResponse.getString("errorMessage");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(add_user.this);
                                    builder.setMessage(errorMessage)
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
            }
        });
    }
    public boolean validate(){
          rgGender = (RadioGroup) findViewById(R.id.rgGender);
     //   selectRadio = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
         String name = etName.getText().toString();
         String username = etUsername.getText().toString();
         String password = etPassword.getText().toString();
//       final String gender = selectRadio.getText().toString();
         String email = etEmail.getText().toString();

        String namePattern = "[a-zA-Z ]+";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        boolean valid = true;
        if(name.isEmpty()||!name.matches(namePattern)){
            valid = false;
            etName.setError("Invalid Full Name");
        }
        else
        {
            etName.setError(null);
        }
        if(username.isEmpty()){
            valid = false;
            etUsername.setError("Username can't be empty");
        }
        else
        {
            etUsername.setError(null);
        }
        if (password.isEmpty()||password.length() < 6)
        {
            valid = false;
            etPassword.setError("Password must at least 6 characters");
        }
        else
        {
            etPassword.setError(null);
        }
       if(rgGender.getCheckedRadioButtonId()== -1){
           valid = false;
           Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
       }
        if(email.isEmpty()||!email.matches(emailPattern)){
            valid = false;
            etEmail.setError("Invalid Email Address");
        }
        else
        {
            etEmail.setError(null);
        }
        return valid;
    }
}