package com.scriptech.vstudy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    private static BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    Toast.makeText(Main2Activity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
                case R.id.navigation_notifications:
                    Toast.makeText(Main2Activity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Fragment fragment;
            fragment = new Home();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer1, fragment);
            fragmentTransaction.commit();
        }
        else
        {

        }
    }

    public static void hideBottomNav(){
        navView.setVisibility(View.GONE);
    }

    public static void showBottomNav(){
        navView.setVisibility(View.VISIBLE);
    }

    public void portrait(){

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void landscape(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}