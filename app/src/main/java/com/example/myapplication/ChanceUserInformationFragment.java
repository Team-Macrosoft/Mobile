package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.myapplication.model.UserUpdateResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class ChanceUserInformationFragment extends Fragment {


    private EditText edittxtFirstName,edittxtSurname,edittxtemail,edittxtUsername,edittxtPassword;
    private Button btnEditUserProfile;
    private LocalDataManager manager = LocalDataManager.getInstance();


    public ChanceUserInformationFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chance_user_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edittxtFirstName = (EditText) view.findViewById(R.id.edittxtFirstNamechg);
        edittxtSurname = (EditText) view.findViewById(R.id.edittxtSurnamechg);
        edittxtemail = (EditText) view.findViewById(R.id.edittxtemailchg);
        edittxtUsername = (EditText) view.findViewById(R.id.edittxtusernamechg);
        edittxtPassword = (EditText) view.findViewById(R.id.edittxtpasswordchg) ;
        btnEditUserProfile = (Button) view.findViewById(R.id.btnEditUserProfile) ;

        String edittxtFirstNameUpt = manager.getSharedPreference(getContext(),"firstName","");
        edittxtFirstName.setHint(edittxtFirstNameUpt);

        String txtsurnameUpt = manager.getSharedPreference(getContext(),"surName","");
        edittxtSurname.setHint(txtsurnameUpt);

        String edittxtemailUpt = manager.getSharedPreference(getContext(),"email","");
        edittxtemail.setHint(edittxtemailUpt);

        String edittxtusernameUpt = manager.getSharedPreference(getContext(),"userName","");
        edittxtUsername.setHint(edittxtusernameUpt);


        btnEditUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInformation();
            }
        });

    }



    private void updateUserInformation(){
        String email = edittxtemail.getText().toString();
        String firstname = edittxtFirstName.getText().toString();
        String surname = edittxtSurname.getText().toString();
        String username = edittxtUsername.getText().toString();
        String password = edittxtPassword.getText().toString();
        String userId = manager.getSharedPreference(getContext(),"Id","");
        String token = manager.getSharedPreference(getContext(),"token","");

        try {



            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = Constant.url+"/api/user/update/"+userId;
            JSONObject jsonBody = new JSONObject();
            if (!username.isEmpty()){jsonBody.put("userName", username);}
            if (!email.isEmpty()){jsonBody.put("email",email);}
            if (!surname.isEmpty()){jsonBody.put("surName",surname);}
            if (!password.isEmpty()){jsonBody.put("password",password);}
            if (!firstname.isEmpty()){jsonBody.put("firstName",firstname);}

            JsonObjectRequest regueest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getContext(), "Your account has been successfully updeted", Toast.LENGTH_SHORT).show();
                    manager.clearSharedPreference(getContext());
                    Gson gson = new Gson();
                    String c = response.toString();
                    UserUpdateResponse userUpdateResponse = gson.fromJson(c, UserUpdateResponse.class);
                    manager.setSharedPreference(getContext(),"firstName",userUpdateResponse.getUser().getFirstName());
                    manager.setSharedPreference(getContext(),"surName",userUpdateResponse.getUser().getSurName());
                    manager.setSharedPreference(getContext(),"email",userUpdateResponse.getUser().getEmail());
                    manager.setSharedPreference(getContext(),"userName",userUpdateResponse.getUser().getUserName());
                    manager.setSharedPreference(getContext(),"Id",userUpdateResponse.getUser().getId().toString());
                    Intent UserInformationFragmentIntent = new Intent(getContext(),MainActivity.class);
                    startActivity(UserInformationFragmentIntent);
                    try {
                        manager.setSharedPreference(getContext(),"token",response.getString("token"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("token",token);
                    return headers;
                }
            };


            requestQueue.add(regueest);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }



    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}