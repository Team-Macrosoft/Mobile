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


public class LoginActivity extends AppCompatActivity {

    private Toolbar actionbarLogin;
    private EditText txtUsername, txtPassword;
    private Button btnApply;
    private LocalDataManager manager = LocalDataManager.getInstance();

    public void init(){
        actionbarLogin = (Toolbar) findViewById(R.id.actionbarLogin);
        setSupportActionBar(actionbarLogin);
        getSupportActionBar().setTitle(R.string.loginActivityInitSetTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtUsername  = (EditText) findViewById(R.id.txtLoginUsername);
        txtPassword  = (EditText) findViewById(R.id.txtLoginPassword);
        btnApply  = (Button) findViewById(R.id.btnLoginApply);
        String token=  manager.getSharedPreference(getApplicationContext(),"token","");
        if(token!=null && !token.isEmpty()){
            Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(mainIntent);
            finish();
        }
        Toast.makeText(LoginActivity.this, R.string.loginActivityInitTost, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, R.string.loginActivityLoginUserAlertEmptyUsername, Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.loginActivityLoginUserAlertEmptyPassword, Toast.LENGTH_SHORT).show();
        }else {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                String URL = Constant.url +"/api/auth";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("userName", username);
                jsonBody.put("password", password);

                JsonObjectRequest regueest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response", response.toString());
                        Gson gson = new Gson();
                        LoginResponse loginResponseUserInf = gson.fromJson(response.toString(), LoginResponse.class);
                        manager.setSharedPreference(getApplicationContext(),"firstName",loginResponseUserInf.getResponse().getFirstName());
                        manager.setSharedPreference(getApplicationContext(),"surName",loginResponseUserInf.getResponse().getSurName());
                        manager.setSharedPreference(getApplicationContext(),"email",loginResponseUserInf.getResponse().getEmail());
                        manager.setSharedPreference(getApplicationContext(),"userName",loginResponseUserInf.getResponse().getUserName());
                        manager.setSharedPreference(getApplicationContext(),"Id",loginResponseUserInf.getResponse().getId().toString());
                        try {
                            manager.setSharedPreference(getApplicationContext(),"token",response.getString("token"));
                            Toast.makeText(LoginActivity.this, R.string.loginActivityLoginUserOnResponseSuscssesLogin, Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, R.string.loginActivityLoginUserOnErrorResponse, Toast.LENGTH_SHORT).show();
                    }
                });


                requestQueue.add(regueest);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}