package com.example.myapplication;

import static java.time.LocalDateTime.of;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;

public class ChooseTimeActivity extends AppCompatActivity {

    NumberPicker startNumberPicker;
    NumberPicker endNumberPicker;
    DatePicker datePicker;
    Button btnReserve;
    private LocalDataManager manager = LocalDataManager.getInstance();
    private Toolbar actionbarChooseTime;


    public void init() {

        actionbarChooseTime = (Toolbar) findViewById(R.id.actionbarMain);
        setSupportActionBar(actionbarChooseTime);
        getSupportActionBar().setTitle(R.string.chooseTimeActivitySetTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                    if(Integer.valueOf(getStartTime().substring(11,13)) < Integer.valueOf(getEndTime().substring(11,13))){
                        Intent mainIntent = new Intent(ChooseTimeActivity.this, MainActivity.class);
                        mainIntent.putExtra("StartTime",getStartTime());
                        mainIntent.putExtra("EndTime",getEndTime());
                        startActivity(mainIntent);
                        finish();
                    }else{
                        Toast.makeText(ChooseTimeActivity.this, "Start time cannot be smaller than end time!", Toast.LENGTH_LONG).show();
                    }

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
        String stringMonth = (month < 10) ? "0"+ month : String.valueOf(month);
        String stringDay = (day < 10) ? "0"+ day : String.valueOf(day);

        String sDate = stringYear+"-"+stringMonth+"-"+stringDay+" "+ stringEndHour+":"
                +stringMinute+":"+stringSecond;



        Log.i("Time", sDate);
        return sDate;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.homeOptionMenu:
                Intent HomeIntent = new Intent(ChooseTimeActivity.this, HomeActivity.class);
                startActivity(HomeIntent);
                return true;
            case R.id.myReservationsOptionMenu:
                Intent myReservationsIntent = new Intent(ChooseTimeActivity.this, MyReservationsActivity.class);
                startActivity(myReservationsIntent);
                return true;
            case R.id.userInfoOptionMenu:
                Intent userProfile = new Intent(ChooseTimeActivity.this, UserProfileActivity.class);
                startActivity(userProfile);
                finish();
                return true;
            case R.id.optionMenuLogout:
                manager.clearSharedPreference(getApplicationContext());
                String token =  manager.getSharedPreference(getApplicationContext(),"token","");
                if(token==null || token.isEmpty()){
                    Intent welcomeIntent = new Intent(ChooseTimeActivity.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                }
                Toast.makeText(getApplicationContext(),R.string.userProfileActivityonOptionsItemSelected,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
