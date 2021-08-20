package com.scriptech.vstudy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;


public class Main2Activity extends AppCompatActivity {

    private static BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            fragment = new Home();
            switch (item.getItemId()) {
                case R.id.navigation_aboutus:
                    fragment = new AboutUs();
                    break;
                case R.id.navigation_feedback:
                    fragment = new Feedback();
                    break;
                case R.id.navigation_home:
                    fragment = new Home();
                    break;
                case R.id.navigation_notifications:
                    fragment = new Notifications();
                    break;
                case R.id.navigation_profile:
                    fragment = new Profile();
                    break;

            }


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
            fragmentTransaction.replace(R.id.frameContainer1, fragment);
            fragmentTransaction.commit();

            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

    }

    public void hideBottomNav() {
        navView.setVisibility(View.GONE);
    }

    public void showBottomNav() {
        navView.setVisibility(View.VISIBLE);
    }

    public void portrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void landscape() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}