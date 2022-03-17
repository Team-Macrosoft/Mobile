package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.myapplication.model.CreateReservationResponse;
import com.example.myapplication.model.ParkingSpot;
import com.example.myapplication.model.ParkingSlotResponse;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MakeAReservationFragment extends Fragment {

    private LocalDataManager manager = LocalDataManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_make_a_reservation, container, false);
        ArrayList<String> parkingSlotStringList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String getAvailableURL = Constant.url + "/api/reservations/getavaiable";


        try {

            String startTime = getActivity().getIntent().getExtras().getString("StartTime","");
            String endTime = getActivity().getIntent().getExtras().getString("EndTime","");


                Log.i("dateeeeeeee", startTime);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("startDate", startTime);
            jsonBody.put("endDate",endTime );

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getAvailableURL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String token = manager.getSharedPreference(getContext(), "token", "");
                    try {
                        ParkingSlotResponse parkingSlotResponse = ParkingSlotResponse.convertFromJSONToMyClass(response);
                        ArrayList<ParkingSpot> parkingSpotList = parkingSlotResponse.getParkingSlotList();

                        for (ParkingSpot parkingSlot : parkingSpotList) {
                            String parkingSpotString = "";
                            parkingSpotString +=  parkingSlot.getId()+".parking spot " + " : " + "price: " + parkingSlot.getPrice();
                            parkingSlotStringList.add(parkingSpotString);
                        }

                        ListView listView = view.findViewById(R.id.slotList);

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parkingSlotStringList);

                        listView.setAdapter(arrayAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                ArrayList<String> reservationStringList = new ArrayList<>();
                                String idString = ((TextView) view).getText().toString().substring(0,1);

                                System.out.println("xxxxxxxxxxxxxxxxxx:"+  idString);

                                String getReservationUrl = Constant.url + "/api/reservations/create";
                                Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
                                try {

                                    JSONObject jsonBody = new JSONObject();
                                    jsonBody.put("userId", manager.getSharedPreference(getContext(),"Id",""));
                                    jsonBody.put("parkingSpotId", idString);
                                    jsonBody.put("startDate", "2022-03-21 10:00:00");
                                    jsonBody.put("endDate", "2022-03-22 10:00:00");

                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getReservationUrl, jsonBody, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Gson gson = new Gson();
                                            CreateReservationResponse createReservationResponse = gson.fromJson(response.toString(),CreateReservationResponse.class);

                                            Log.i("VOLLEY", response.toString());

                                                Toast.makeText(getContext(), "You reservation is created", Toast.LENGTH_LONG).show();
                                                Intent mainIntent = new Intent(getContext(), BillActivity.class);
                                                mainIntent.putExtra("reservationId", String.valueOf(createReservationResponse.getData().getId()));
                                                startActivity(mainIntent);

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("VOLLEY", error.toString());
                                        }
                                    }) {
                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
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
                    String token = manager.getSharedPreference(getContext(), "token", "");
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization",token);
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }
}