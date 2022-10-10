//package com.scriptech.vstudy.ui.activities.mainActivity1;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.scriptech.vstudy.databinding.ActivityMainBinding;
//import com.scriptech.vstudy.ui.fragments.intros.Intro1;
//import com.scriptech.vstudy.R;
//import com.scriptech.vstudy.ui.activities.mainActivity2.Main2Activity;

//public class MainActivity extends AppCompatActivity {
//
//    ImageView splash_logo;
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                getWindow().setStatusBarColor(getColor(R.color.white));
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        }
//
//
//        splash_logo = findViewById(R.id.splash_logo);
//
//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                splash_logo.setVisibility(View.GONE);
//                if (auth.getCurrentUser() != null) {
//                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Fragment fragment;
//                    fragment = new Intro1();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frameContainer, fragment);
//                    fragmentTransaction.commit();
//                }
//            }
//        }, 3000);
//    }
//
//}
