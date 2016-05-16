package com.example.user.studentdatamanagementsystem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    EditText etsID;
    EditText etsName;
    EditText etsIC;
    RadioGroup rbsGender;
    RadioButton selectRadio;
    RadioGroup rbsFaculty;
    RadioButton selectRadioF;
    RadioGroup rbsRace;
    RadioButton selectRadioR;
    EditText etsDate;
    EditText etsTel;
    EditText etsEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etsID = (EditText) findViewById(R.id.etID);
        etsName = (EditText) findViewById(R.id.etName);
        etsIC = (EditText) findViewById(R.id.etIC);
        rbsGender = (RadioGroup) findViewById(R.id.rbGender);
        rbsFaculty = (RadioGroup) findViewById(R.id.rbFaculty);
        rbsRace = (RadioGroup) findViewById(R.id.rbRace);
        etsDate = (EditText) findViewById(R.id.etDate);
        etsTel = (EditText) findViewById(R.id.etTel);
        etsEmail = (EditText) findViewById(R.id.etEmail);
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
                if (validate()) {
                    //optional
                    final ProgressDialog progressDialog = new ProgressDialog(AddStudent.this);
                    progressDialog.setMessage("Saving...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    //optional

                    selectRadio = (RadioButton) findViewById(rbsGender.getCheckedRadioButtonId());
                    selectRadioF = (RadioButton) findViewById(rbsFaculty.getCheckedRadioButtonId());
                    selectRadioR = (RadioButton) findViewById(rbsRace.getCheckedRadioButtonId());

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
                                    //go to another activity
                                    final Intent intent = new Intent(AddStudent.this, Display.class);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddStudent.this);
                                    builder.setMessage("Save sucessfully")
                                            .setPositiveButton("Okay", new Dialog.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    AddStudent.this.startActivity(intent);
                                                }
                                            });
                                    builder.create()
                                            .show();
                                } else {
                                    //optional
                                    progressDialog.dismiss();
                                    String errorMessage = jsonResponse.getString("errorMessage");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddStudent.this);
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
                    AddStudentRequest addStudentRequest = new AddStudentRequest(sID, sName, sIC, sGender, sFaculty, sRace, sDate, sTel, sEmail, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AddStudent.this);
                    queue.add(addStudentRequest);
                }
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
//                new DatePickerDialog(AddStudent.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddStudent.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });



    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        setDate.setText(sdf.format(myCalendar.getTime()));
    }
    public boolean validate(){
        rbsGender = (RadioGroup) findViewById(R.id.rbGender);
        rbsFaculty = (RadioGroup) findViewById(R.id.rbFaculty);
        rbsRace = (RadioGroup) findViewById(R.id.rbRace);
        String sID = etsID.getText().toString();
        String sName = etsName.getText().toString();
        String sIC = etsIC.getText().toString();
//        String sGender = selectRadio.getText().toString();
//        String sFaculty = selectRadioF.getText().toString();
//        String sRace = selectRadioR.getText().toString();
        String sDate = etsDate.getText().toString();
        String sTel = etsTel.getText().toString();
         String sEmail = etsEmail.getText().toString();

        String namePattern = "[a-zA-Z ]+";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String idPattern ="^([B]+[0-9+]+)$";
        String ICPattern ="\\d{6}[\\-]\\d{2}[\\-]\\d{4}";
        String TelPattern ="\\d{3}[\\-]\\d{7}";


        boolean valid = true;
        if(!sID.matches(idPattern)){
            valid = false;
            etsID.setError("Format: B031410111");
        }
        else
        {
            etsID.setError(null);
        }
        if(sName.isEmpty()||!sName.matches(namePattern)){
            valid = false;
            etsName.setError("Invalid Full Name");
        }
        else
        {
            etsName.setError(null);
        }
        if(!sIC.matches(ICPattern)){
            valid = false;
            etsIC.setError("Format: 980201-02-5421");
        }
        else
        {
            etsIC.setError(null);
        }
        if(rbsGender.getCheckedRadioButtonId()== -1){
            valid = false;
            Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
        }
        if(rbsFaculty.getCheckedRadioButtonId()== -1){
            valid = false;
            Toast.makeText(getApplicationContext(), "Please select Faculty", Toast.LENGTH_SHORT).show();
        }
        if(rbsRace.getCheckedRadioButtonId()== -1){
            valid = false;
            Toast.makeText(getApplicationContext(), "Please select Race", Toast.LENGTH_SHORT).show();
        }
        if(sDate.isEmpty()){
            valid = false;
            etsDate.setError("Date of Birth can't be empty");
        }
        else
        {
            etsDate.setError(null);
        }
        if(!sTel.matches(TelPattern)){
            valid = false;
            etsTel.setError("Format: 012-3456789");
        }
        else
        {
            etsTel.setError(null);
        }
        if(sEmail.isEmpty()||!sEmail.matches(emailPattern)){
            valid = false;
            etsEmail.setError("Invalid Email Address");
        }
        else
        {
            etsEmail.setError(null);
        }
        return valid;
    }


}
