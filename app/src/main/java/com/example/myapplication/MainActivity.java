package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.model.ParkingSlotResponse;
import com.example.myapplication.model.ParkingSpot;
import com.example.myapplication.model.ReservationResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Toolbar actionbarMain;
    private LocalDataManager manager = LocalDataManager.getInstance();
    private Button btnRefresh;


    public void init() {


        actionbarMain = (Toolbar) findViewById(R.id.actionbarMain);
        setSupportActionBar(actionbarMain);
        getSupportActionBar().setTitle(R.string.mainActivityInitSetTitle);

        btnRefresh = findViewById(R.id.btnRefresh);


        ArrayList<String> parkingSlotStringList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String getAvailableURL = Constant.url + "/api/reservations/getavaiable";


        try {
            String startTime =   getIntent().getExtras().getString("StartTime", "");
            String endTime = getIntent().getExtras().getString("EndTime", "");

//            if (getIntent().getExtras() != null){
//                getIntent().getExtras().getString("StartTime", "");
//                endTime = getIntent().getExtras().getString("EndTime", "");
//            }


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("startDate", startTime);
            jsonBody.put("endDate", endTime);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getAvailableURL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ParkingSlotResponse parkingSlotResponse = ParkingSlotResponse.convertFromJSONToMyClass(response);
                        ArrayList<ParkingSpot> parkingSpotList = parkingSlotResponse.getParkingSlotList();

                        if (parkingSpotList != null && parkingSpotList.size() != 0) {
                            for (ParkingSpot parkingSlot : parkingSpotList) {
                                String parkingSpotString = "";
                                parkingSpotString += parkingSlot.getId() + ".parking spot " + " : " + "price: " + parkingSlot.getPrice();
                                parkingSlotStringList.add(parkingSpotString);
                            }
                            ListView listView = findViewById(R.id.slotList);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parkingSlotStringList);

                            listView.setAdapter(arrayAdapter);


                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                                    ArrayList<String> reservationStringList = new ArrayList<>();
                                    String idString = ((TextView) view).getText().toString().substring(0, 1);

                                    System.out.println("xxxxxxxxxxxxxxxxxx:" + idString);

                                    String getReservationUrl = Constant.url + "/api/reservations/create";
                                    try {

                                        JSONObject jsonBody = new JSONObject();
                                        jsonBody.put("userId", manager.getSharedPreference(MainActivity.this, "Id", ""));
                                        jsonBody.put("parkingSpotId", idString);
                                        jsonBody.put("startDate", startTime);
                                        jsonBody.put("endDate", endTime);

                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getReservationUrl, jsonBody, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Gson gson = new Gson();
                                                ReservationResponse reservationResponse = gson.fromJson(response.toString(), ReservationResponse.class);

                                                Log.i("VOLLEY", response.toString());

                                                Toast.makeText(MainActivity.this, "You reservation is created", Toast.LENGTH_LONG).show();
                                                Intent mainIntent = new Intent(MainActivity.this, BillActivity.class);
                                                mainIntent.putExtra("reservationId", String.valueOf(reservationResponse.getData().getId()));
                                                startActivity(mainIntent);

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(MainActivity.this, "This spot is not available, please refresh the page", Toast.LENGTH_LONG).show();
                                                Log.e("VOLLEYzzzzzzzzzzzzzzzz", error.toString());
                                            }
                                        }) {
                                            @Override
                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                String token = manager.getSharedPreference(MainActivity.this, "token", "");
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("Authorization", token);
                                                return params;
                                            }

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/json; charset=utf-8";
                                            }
                                        };

                                        requestQueue.add(jsonObjectRequest);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });


                        }else {
                            Toast.makeText(MainActivity.this, "There is not any available spot. " +
                                    "Please try another time slot", Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String token = manager.getSharedPreference(MainActivity.this, "token", "");
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", token);
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.homeOptionMenu:
                Intent HomeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(HomeIntent);
                return true;
            case R.id.myReservationsOptionMenu:
                Intent MainIntent = new Intent(MainActivity.this, MyReservationsActivity.class);
                startActivity(MainIntent);
                return true;
            case R.id.userInfoOptionMenu:
                Intent userProfile = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(userProfile);
                return true;
            case R.id.optionMenuLogout:
                manager.clearSharedPreference(getApplicationContext());
                String token = manager.getSharedPreference(getApplicationContext(), "token", "");
                if (token == null || token.isEmpty()) {
                    Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                }
                Toast.makeText(getApplicationContext(), R.string.userProfileActivityonOptionsItemSelected, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}