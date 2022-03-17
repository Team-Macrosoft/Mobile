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
                    Intent mainIntent = new Intent(ChooseTimeActivity.this, MainActivity.class);
                    mainIntent.putExtra("StartTime",getStartTime());
                    mainIntent.putExtra("EndTime",getEndTime());
                    startActivity(mainIntent);
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

    private String getEndTime() throws ParseException {



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


        Log.i("Time", sDate);
        return sDate;

    }

}
