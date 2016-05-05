package com.example.user.studentdatamanagementsystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 3/5/2016.
 */
public class SignUpRequest extends StringRequest {
    private static final String SIGNUP_REQUEST_URL = "http://clover.comli.com/SignUp.php";
    private Map<String, String> params;

    public SignUpRequest(String name, String username, String password, String gender, String email,  Response.Listener<String> listener){
        super(Method.POST, SIGNUP_REQUEST_URL, listener, null);
        params  = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("gender", gender);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
