package com.example.user.studentdatamanagementsystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user-pc on 10/5/2016.
 */
public class SearchPageRequest extends StringRequest {

    private static final String SEARCH_PAGE_REQUEST_URL = "http://clover.comli.com/search.php";
    private Map<String, String> params;

    public SearchPageRequest(String sID,  Response.Listener<String> listener){
        super(Method.POST, SEARCH_PAGE_REQUEST_URL, listener, null);
        params  = new HashMap<>();
        params.put("sID", sID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}