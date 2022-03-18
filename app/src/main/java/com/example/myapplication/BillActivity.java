package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.model.ReservationResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BillActivity extends AppCompatActivity {

   TextView txtStartDate;
   TextView txtEndDate;
   TextView txtTotalCost;

    Button btnGoToHome;

    private LocalDataManager manager = LocalDataManager.getInstance();

    public void init() {
        RequestQueue requestQueue = Volley.newRequestQueue(BillActivity.this);

        btnGoToHome = findViewById(R.id.btnGoToHome);

        txtStartDate = findViewById(R.id.textStartDate);
        txtEndDate =  findViewById(R.id.textEndDate);
        txtTotalCost = findViewById(R.id.textTotalCost);



        String rId =  getIntent().getStringExtra("reservationId");
        String url = Constant.url + "/api/reservations/"+rId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                ReservationResponse reservationResponse = gson.fromJson(response.toString(), ReservationResponse.class);

                String startDateFromArray = reservationResponse.getData().getStartDate()[0]
                        + "-" + reservationResponse.getData().getStartDate()[1]
                        + "-" + reservationResponse.getData().getStartDate()[2]
                        + ":" + reservationResponse.getData().getStartDate()[3]
                        + ":" + reservationResponse.getData().getStartDate()[4];

                String endDateFromArray = reservationResponse.getData().getEndDate()[0]
                        + "-" + reservationResponse.getData().getEndDate()[1]
                        + "-" + reservationResponse.getData().getEndDate()[2]
                        + ":" + reservationResponse.getData().getEndDate()[3]
                        + ":" + reservationResponse.getData().getEndDate()[4];


                txtStartDate.setText(startDateFromArray);
                txtEndDate.setText(endDateFromArray);
                txtTotalCost.setText(String.valueOf(reservationResponse.getData().getTotalPrice()));

                Log.i("VOLLEY", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = manager.getSharedPreference(BillActivity.this,"token","");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        init();

        btnGoToHome.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent billIntent = new Intent(BillActivity.this,HomeActivity.class);
            startActivity(billIntent);
        }
    });
}
}
