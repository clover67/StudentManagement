package com.example.user.studentdatamanagementsystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 4/5/2016.
 */
public class AddStudentRequest extends StringRequest {
    private static final String ADDSTUDENT_REQUEST_URL = "http://clover.comli.com/AddStudent.php";
    private Map<String, String> params;

    public AddStudentRequest(String sID, String sName, String sIC, String sGender, String sFaculty, String sRace, String sDOB, String sTelNo, String sEmail,  Response.Listener<String> listener){
        super(Method.POST, ADDSTUDENT_REQUEST_URL, listener, null);
        params  = new HashMap<>();
        params.put("sID", sID);
        params.put("sName", sName);
        params.put("sIC", sIC);
        params.put("sGender", sGender);
        params.put("sFaculty", sFaculty);
        params.put("sRace", sRace);
        params.put("sDOB", sDOB);
        params.put("sTelNo", sTelNo);
        params.put("sEmail", sEmail);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
