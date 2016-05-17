package com.example.user.studentdatamanagementsystem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
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

public class UpdateStudent extends AppCompatActivity {

    EditText setDate;
    Calendar myCalendar;
    EditText etID;
    EditText etName;
    EditText etIC;
    RadioGroup rbsGender;
    RadioButton rbSMale;
    RadioButton rbSFemale;
    RadioGroup rbsFaculty;
    RadioButton rbFTMK;
    RadioButton rbFKP;
    RadioButton rbFKEKK;
    RadioButton rbFKE;
    RadioButton rbFTK;
    RadioButton rbFKM;
    RadioButton rbFPTT;
    RadioGroup rbsRace;
    RadioButton rbMalay;
    RadioButton rbCina;
    RadioButton rbIndia;
    RadioButton rbOthers;
    EditText etDOB ;
    EditText etTel ;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

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


        etID = (EditText) findViewById(R.id.etStuID);
        etName = (EditText) findViewById(R.id.etName);
        etIC = (EditText) findViewById(R.id.etIC);
        rbsGender = (RadioGroup) findViewById(R.id.rbGender);
        rbsFaculty = (RadioGroup) findViewById(R.id.rbFaculty);
        rbsRace = (RadioGroup) findViewById(R.id.rbRace);
        etDOB = (EditText) findViewById(R.id.etDate);
        etTel = (EditText) findViewById(R.id.etTel);
        etEmail = (EditText) findViewById(R.id.etEmail);




        etID.setText(sID);
        etName.setText(sName);
        etIC.setText(sIC);

        etDOB.setText(sDOB);
        etTel.setText(sTelNo);
        etEmail.setText(sEmail);


        if(sGender.equals("Male"))
            rbsGender.check(R.id.rbSMale);
        else if(sGender.equals("Female"))
            rbsGender.check(R.id.rbSFemale);

        if(sFaculty.equals("FTMK"))
            rbsFaculty.check(R.id.rbFTMK);
        else if(sFaculty.equals("FKP"))
            rbsFaculty.check(R.id.rbFKP);
        else if(sFaculty.equals("FKEKK"))
            rbsFaculty.check(R.id.rbFKEKK);
        else if(sFaculty.equals("FKE"))
            rbsFaculty.check(R.id.rbFKE);
        else if(sFaculty.equals("FTK"))
            rbsFaculty.check(R.id.rbFTK);
        else if(sFaculty.equals("FKM"))
            rbsFaculty.check(R.id.rbFKM);
        else if(sFaculty.equals("FPTT"))
            rbsFaculty.check(R.id.rbFPTT);

        if(sRace.equals("Malay"))
            rbsRace.check(R.id.rbMalay);
        else if(sRace.equals("Cina"))
            rbsRace.check(R.id.rbCina);
        else if(sRace.equals("India"))
            rbsRace.check(R.id.rbIndia);
        else if(sRace.equals("Others"))
            rbsRace.check(R.id.rbOthers);



        final Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (validate()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStudent.this);
                    builder.setMessage("Are you sure want to update?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new Dialog.OnClickListener() {


                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //optional
                                    final ProgressDialog progressDialog = new ProgressDialog(UpdateStudent.this);
                                    progressDialog.setMessage("Loading...");
                                    progressDialog.setCancelable(false);
                                    progressDialog.show();
                                    //optional

                                    final RadioButton selectRadio = (RadioButton) findViewById(rbsGender.getCheckedRadioButtonId());
                                    final RadioButton selectRadioF = (RadioButton) findViewById(rbsFaculty.getCheckedRadioButtonId());
                                    final RadioButton selectRadioR = (RadioButton) findViewById(rbsRace.getCheckedRadioButtonId());

                                    final String sID = etID.getText().toString();
                                    final String sName = etName.getText().toString();
                                    final String sIC = etIC.getText().toString();
                                    final String sGender = selectRadio.getText().toString();
                                    final String sFaculty = selectRadioF.getText().toString();
                                    final String sRace = selectRadioR.getText().toString();
                                    final String sDate = etDOB.getText().toString();
                                    final String sTel = etTel.getText().toString();
                                    final String sEmail = etEmail.getText().toString();

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");

                                                if (success) {
                                                    //optional
                                                    progressDialog.dismiss();

                                                    final Intent intent = new Intent(UpdateStudent.this, ManagePage.class);
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStudent.this);
                                                    builder.setMessage("Update successfully")
                                                            .setPositiveButton("Okay", new Dialog.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    UpdateStudent.this.startActivity(intent);
                                                                }
                                                            });
                                                    builder.create()
                                                            .show();


                                                } else {
                                                    //optional
                                                    progressDialog.dismiss();

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStudent.this);
                                                    builder.setMessage("Update failed!")
                                                            .setNegativeButton("Retry", null)
                                                            .create()
                                                            .show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    UpdateStudentRequest updateStudentRequest = new UpdateStudentRequest(sID, sName, sIC, sGender, sFaculty, sRace, sDate, sTel, sEmail, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(UpdateStudent.this);
                                    queue.add(updateStudentRequest);

                                }
                            });
                    builder.setNegativeButton("No", new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    builder.create()
                            .show();
                }


            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateStudent.this, ManagePage.class);
                UpdateStudent.this.startActivity(intent);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateStudent.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
        String sID = etID.getText().toString();
        String sName = etName.getText().toString();
        String sIC = etIC.getText().toString();
//        String sGender = selectRadio.getText().toString();
//        String sFaculty = selectRadioF.getText().toString();
//        String sRace = selectRadioR.getText().toString();
        String sDate = etDOB.getText().toString();
        String sTel = etTel.getText().toString();
        String sEmail = etEmail.getText().toString();

        String namePattern = "[a-zA-Z ]+";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String idPattern ="^([B]+[0-9+]+)$";
        String ICPattern ="\\d{6}[\\-]\\d{2}[\\-]\\d{4}";
        String TelPattern ="\\d{3}[\\-]\\d{7}";


        boolean valid = true;
        if(!sID.matches(idPattern)){
            valid = false;
            etID.setError("Format: B031410111");
        }
        else
        {
            etID.setError(null);
        }
        if(sName.isEmpty()||!sName.matches(namePattern)){
            valid = false;
            etName.setError("Invalid Full Name");
        }
        else
        {
            etName.setError(null);
        }
        if(!sIC.matches(ICPattern)){
            valid = false;
            etIC.setError("Format: 980201-02-5421");
        }
        else
        {
            etIC.setError(null);
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
            etDOB.setError("Date of Birth can't be empty");
        }
        else
        {
            etDOB.setError(null);
        }
        if(!sTel.matches(TelPattern)){
            valid = false;
            etTel.setError("Format: 012-3456789");
        }
        else
        {
            etTel.setError(null);
        }
        if(sEmail.isEmpty()||!sEmail.matches(emailPattern)){
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
