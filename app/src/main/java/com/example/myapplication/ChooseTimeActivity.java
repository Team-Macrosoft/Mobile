package com.example.myapplication;

import static java.time.LocalDateTime.of;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.model.ParkingSpot;
import com.example.myapplication.model.ParkingSlotResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChooseTimeActivity extends AppCompatActivity {

    NumberPicker startNumberPicker;
    NumberPicker endNumberPicker;
    DatePicker datePicker;
    Button btnReserve;

    public void init() {
        startNumberPicker = findViewById(R.id.startNumberPicker);
        startNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        startNumberPicker.setMaxValue(24);
        startNumberPicker.setMinValue(8);

        endNumberPicker = findViewById(R.id.endNumberPicker);

        endNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        endNumberPicker.setMaxValue(24);
        endNumberPicker.setMinValue(8);

        datePicker = findViewById(R.id.datePicker1);
        btnReserve = findViewById(R.id.btnReserve);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        init();
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getStartTime();
                    getEndTime();
                    postTimeAndGetSlotList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private String getStartTime() throws ParseException {


        int startHour = startNumberPicker.getValue();
        int endNumber = endNumberPicker.getValue();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        String stringStartHour = (startHour < 10) ? "0"+ startHour : String.valueOf(startHour);
        String stringMinute = "0"+ 0;
        String stringSecond = "0"+ 0;
        String stringYear = String.valueOf(year);
        String stringMonth = (month < 10) ? "0"+ month : String.valueOf(month);
        String stringDay = (day < 10) ? "0"+ day : String.valueOf(day);

        String sDate = stringYear+"-"+stringMonth+"-"+stringDay+" "+ stringStartHour+":"
                +stringMinute+":"+stringSecond;

        Log.i("Time", sDate);
        return sDate;

    }

    private Date getEndTime() throws ParseException {



        int endHour = endNumberPicker.getValue();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        String stringEndHour = (endHour < 10) ? "0"+String.valueOf(endHour) : String.valueOf(endHour);
        String stringMinute = "0"+String.valueOf(0);
        String stringSecond = "0"+String.valueOf(0);
        String stringYear = String.valueOf(year);
        String stringMonth = String.valueOf(month);
        String stringDay = String.valueOf(day);

        String sDate = stringYear+"-"+stringMonth+"-"+stringDay+" "+ stringEndHour+":"
                +stringMinute+":"+stringSecond;


        Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate);

        Log.i("Time", String.valueOf(date1));

        return date1;

    }

    private void postTimeAndGetSlotList() {

        ArrayList<String> parkingSlotStringList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(ChooseTimeActivity.this);
        String URL = Constant.url +"/api/reservations/getavaiable";

        try {

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", 1);
            jsonBody.put("parkingSpotId", 1);
            jsonBody.put("startDate", getStartTime() );
            jsonBody.put("endDate", getStartTime());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonBody ,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("VOLLEY", response.toString());
                    try {
                        ParkingSlotResponse parkingSlotResponse = ParkingSlotResponse.convertFromJSONToMyClass(response);
                        ArrayList<ParkingSpot> parkingSpotList = parkingSlotResponse.getParkingSlotList();


                        for (ParkingSpot parkingSlot : parkingSpotList) {
                            String parkingSpotString = "";
                            parkingSpotString += "parking spot " + parkingSlot.getId() + " : " + "price: " + parkingSlot.getPrice();
                            parkingSlotStringList.add(parkingSpotString);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent mainIntent = new Intent(ChooseTimeActivity.this, MainActivity.class);
                    startActivity(mainIntent);
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

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }
}
