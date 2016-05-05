package com.example.user.studentdatamanagementsystem;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddStudent extends AppCompatActivity {

    EditText setDate;
    Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        final EditText etsID = (EditText) findViewById(R.id.etID);
        final EditText etsName = (EditText) findViewById(R.id.etName);
        final EditText etsIC = (EditText) findViewById(R.id.etIC);
        final RadioGroup rbsGender = (RadioGroup) findViewById(R.id.rbGender);
        final RadioGroup rbsFaculty = (RadioGroup) findViewById(R.id.rbFaculty);
        final RadioGroup rbsRace = (RadioGroup) findViewById(R.id.rbRace);
        final EditText etsDate = (EditText) findViewById(R.id.etDate);
        final EditText etsTel = (EditText) findViewById(R.id.etTel);
        final EditText etsEmail = (EditText) findViewById(R.id.etEmail);
        final Button btnAddCancel = (Button) findViewById(R.id.btnAddCancel);
        final Button btnAddSave = (Button) findViewById(R.id.btnAddSave);

        btnAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStudent.this, ManagePage.class);
                AddStudent.this.startActivity(intent);
            }
        });

        btnAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //optional
                final ProgressDialog progressDialog = new ProgressDialog(AddStudent.this);
                progressDialog.setMessage("Saving...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //optional

                final RadioButton selectRadio = (RadioButton) findViewById(rbsGender.getCheckedRadioButtonId());
                final RadioButton selectRadioF = (RadioButton) findViewById(rbsFaculty.getCheckedRadioButtonId());
                final RadioButton selectRadioR = (RadioButton) findViewById(rbsRace.getCheckedRadioButtonId());

                final String sID = etsID.getText().toString();
                final String sName = etsName.getText().toString();
                final String sIC = etsIC.getText().toString();
                final String sGender = selectRadio.getText().toString();
                final String sFaculty = selectRadioF.getText().toString();
                final String sRace = selectRadioR.getText().toString();
                final String sDate = etsDate.getText().toString();
                final String sTel = etsTel.getText().toString();
                final String sEmail = etsEmail.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                progressDialog.dismiss();
                                Context context = getApplicationContext();
                                CharSequence text = "Save Successfully!";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //go to another activity
                                Intent intent = new Intent(AddStudent.this, ManagePage.class);
                                //optional
                                AddStudent.this.startActivity(intent);
                            } else {
                                //optional
                                progressDialog.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(AddStudent.this);
                                builder.setMessage("Save failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                AddStudentRequest addStudentRequest = new AddStudentRequest(sID,sName, sIC, sGender,sFaculty,sRace,sDate,sTel, sEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddStudent.this);
                queue.add(addStudentRequest);
            }
        });

        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        setDate = (EditText) findViewById(R.id.etDate);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddStudent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        setDate.setText(sdf.format(myCalendar.getTime()));
    }


}
