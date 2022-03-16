package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;

public class HomeActivity extends AppCompatActivity {
    private Button btnGoToReservation;
    private TextView txtHome;

    public void init() {
        btnGoToReservation = findViewById(R.id.btnGoToReservation);
        txtHome = findViewById(R.id.txtHome);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        btnGoToReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(HomeActivity.this, ChooseTimeActivity.class);
                startActivity(homeIntent);
            }
        });



    }
}
