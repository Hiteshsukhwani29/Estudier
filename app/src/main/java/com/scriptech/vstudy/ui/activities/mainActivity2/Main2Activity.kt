package com.scriptech.vstudy.ui.activities.mainActivity2

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.scriptech.vstudy.R
import com.scriptech.vstudy.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = this.findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }

    fun hideBottomNav() {
        binding.navView.setVisibility(GONE);
    }

    fun showBottomNav() {
        binding.navView.setVisibility(VISIBLE);
    }

    fun portrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    fun landscape() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}