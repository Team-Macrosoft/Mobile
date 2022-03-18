package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class UserProfileActivity extends AppCompatActivity {


    private Toolbar actionbarUserProfile;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LocalDataManager manager = LocalDataManager.getInstance();


    public void init(){
        actionbarUserProfile = (Toolbar) findViewById(R.id.actionbarUserProfile);
        setSupportActionBar(actionbarUserProfile);
        getSupportActionBar().setTitle(R.string.userProfileActivityInitSetTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.UserProfileTabLayOut);
        viewPager = (ViewPager) findViewById(R.id.userProfileViewPager);

        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new UserInformationFragment(),"User Information");
        vpAdapter.addFragment(new ChanceUserInformationFragment(),"Update User Information");
        viewPager.setAdapter(vpAdapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
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
            case R.id.reservationHomeOptionMenu:
                Intent HomeIntent = new Intent(UserProfileActivity.this, HomeActivity.class);
                startActivity(HomeIntent);
                return true;
            case R.id.reservationReservationOptionMenu:
                    Intent MainIntent = new Intent(UserProfileActivity.this, MyReservationActivity.class);
                    startActivity(MainIntent);
                return true;
            case R.id.reservationInfoOptionMenu:
                Intent userProfile = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                startActivity(userProfile);
                finish();
                return true;
            case R.id.optionMenuLogout:
                manager.clearSharedPreference(getApplicationContext());
                String token =  manager.getSharedPreference(getApplicationContext(),"token","");
                if(token==null || token.isEmpty()){
                    Intent welcomeIntent = new Intent(UserProfileActivity.this, WelcomeActivity.class);
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