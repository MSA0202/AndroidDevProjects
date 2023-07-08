package com.example.covidwatch;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.io.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {


    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.f1Fragment, new HomePage()).commit();

    }
    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.homePage:
                    selectedFragment = new HomePage();
                    break;
                case R.id.trackCovid:
                    selectedFragment = new TrackCovid();
                    break;
                case R.id.covidCheck:
                    selectedFragment = new CovidCheck();
                    break;
                case R.id.checkIn:
                    selectedFragment = new CheckIn();
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.f1Fragment, selectedFragment)
                    .commit();
            return true;
        }
    };
}