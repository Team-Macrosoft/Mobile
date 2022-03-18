package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;

public class HomeActivity extends AppCompatActivity {
    private Button btnGoToReservation;
    private TextView txtHomeWelcome,txtHomeName;
    private Toolbar actionbarHome;
    private LocalDataManager manager = LocalDataManager.getInstance();

    public void init() {
        actionbarHome = (Toolbar) findViewById(R.id.actionbarMain);
        setSupportActionBar(actionbarHome);
        getSupportActionBar().setTitle(R.string.homeActivitySetTitle);


        btnGoToReservation = findViewById(R.id.btnGoToReservation);
        txtHomeWelcome = findViewById(R.id.texttxtHomeWelcome);
        txtHomeName = findViewById(R.id.texttxtHomeName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        welcome();
        btnGoToReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(HomeActivity.this, ChooseTimeActivity.class);
                startActivity(homeIntent);
            }
        });

    }

    public  void welcome(){

        txtHomeName.setText(manager.getSharedPreference(getApplicationContext(),"firstName",""));
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
            case R.id.reservationReservationOptionMenu:
                Intent MainIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(MainIntent);
                return true;
            case R.id.reservationInfoOptionMenu:
                Intent userProfile = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(userProfile);
                finish();
                return true;
            case R.id.optionMenuLogout:
                manager.clearSharedPreference(getApplicationContext());
                String token =  manager.getSharedPreference(getApplicationContext(),"token","");
                if(token==null || token.isEmpty()){
                    Intent welcomeIntent = new Intent(HomeActivity.this, WelcomeActivity.class);
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
