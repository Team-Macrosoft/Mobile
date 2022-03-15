package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button  btnRegister, btnAccount;
    private LocalDataManager manager = LocalDataManager.getInstance();

    public void init(){
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnAccount = (Button) findViewById(R.id.btnAccount);

        String token=  manager.getSharedPreference(getApplicationContext(),"token","");

        if(token!=null && !token.isEmpty()){
            Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                finish();
            }
        });


        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });
    }
}