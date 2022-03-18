package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.model.LoginResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar actionbarRegister;
    private EditText txtusername, txtpasswoord, txtemail, txtFirstname, txtSurname;
    private Button btnApply;

    public void init(){
        actionbarRegister = (Toolbar) findViewById(R.id.actionbarRegister);
        setSupportActionBar(actionbarRegister);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtusername = (EditText) findViewById(R.id.txtRegisterUsername);
        txtpasswoord = (EditText) findViewById(R.id.txtRegisterPassword);
        txtemail = (EditText) findViewById(R.id.txtRegisterEmailAddress);
        txtFirstname = (EditText) findViewById(R.id.txtRegisterFirstname);
        txtSurname = (EditText) findViewById(R.id.txtRegisterSurname);

        btnApply = (Button) findViewById(R.id.btnRegisterApply);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    public void createNewAccount(){try {
        String username = txtusername.getText().toString();
        String password = txtpasswoord.getText().toString();
        String email = txtemail.getText().toString();
        String firstname = txtFirstname.getText().toString();
        String surname = txtSurname.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        String URL = Constant.url + "/api/auth/register";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("userName", username);
        jsonBody.put("email",email);
        jsonBody.put("password", password);
        jsonBody.put("surName",surname);
        jsonBody.put("firstName",firstname);
        jsonBody.put("role","ROLE_MEMBER");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                Toast.makeText(RegisterActivity.this, R.string.registerActivityonResponse, Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, R.string.registerActivityonErrorResponse, Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(request);
    }

    catch (JSONException e) {
        e.printStackTrace();
    }}

}