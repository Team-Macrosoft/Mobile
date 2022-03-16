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
import com.example.myapplication.model.Reservation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;


public class MakeAReservationFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void createReservation(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<String> parkingSlotList = new ArrayList<>();
        parkingSlotList.add("asd");
        parkingSlotList.add("asd");
        parkingSlotList.add("asd");

        View view = inflater.inflate(R.layout.fragment_make_a_reservation, container, false);

        ListView listView = view.findViewById(R.id.slotList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parkingSlotList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                ArrayList<String> reservationStringList = new ArrayList<>();
                String URL = Constant.url +"/api/reservations/create";
                Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
                try {

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("userId", 1);
                    jsonBody.put("parkingSpotId", 1);
                    jsonBody.put("startDate", "2022-03-20 10:00:00" );
                    jsonBody.put("endDate", "2022-03-21 10:00:00");

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonBody ,new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("VOLLEY", response.toString());
                            try {
                                CreateReservationResponse reservationResponse = CreateReservationResponse.convertFromJSONToMyClass(response);
                                ArrayList<Reservation> reservations = reservationResponse.getReservationArrayList();

                                for (Reservation reservation : reservations) {
                                    String reservationString = "";
                                    reservationString += "Total price: " + reservation.getTotalPrice() + " : " + "Parking spot: " + reservation.getParkingSpot().getId();
                                    reservationStringList.add(reservationString);

                                }
                                for (String res: reservationStringList
                                     ) {
                                    System.out.println(res);
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
                    };

                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }
}