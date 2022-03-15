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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.Constant;

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

    private void createNewAccount() {

        String username = txtusername.getText().toString();
        String password = txtpasswoord.getText().toString();
        String email = txtemail.getText().toString();
        String firstname = txtFirstname.getText().toString();
        String surname = txtSurname.getText().toString();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        }else {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                String URL = Constant.url + "/api/auth/register";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("userName", username);
                jsonBody.put("email",email);
                jsonBody.put("password", password);
                jsonBody.put("surName",surname);
                jsonBody.put("firstName",firstname);

                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        Toast.makeText(RegisterActivity.this, "Your account has been successfully created", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                        Toast.makeText(RegisterActivity.this, "Your account could not be created", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return  Response.success(responseString,HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                requestQueue.add(stringRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}