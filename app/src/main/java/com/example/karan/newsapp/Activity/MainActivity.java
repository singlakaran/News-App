package com.example.karan.newsapp.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.karan.newsapp.Fragment.HomeFragment;
import com.example.karan.newsapp.Fragment.NewCategoryFragment;
import com.example.karan.newsapp.Fragment.SplashFragment;
import com.example.karan.newsapp.Libs.Utils;
import com.example.karan.newsapp.Fragment.NewsFragment;
import com.example.karan.newsapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.*;

public class MainActivity extends  AppCompatActivity {

//    @BindView(R.id.navigation)
    private BottomNavigationView bottomNavigationView;
//    @BindView(R.id.main_fragment_container)
    private LinearLayout mainFragmentContainer;
    private LinearLayout splashScreen;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        bottomNavigationView = findViewById(R.id.navigation);
        mainFragmentContainer = findViewById(R.id.main_fragment_container);
        splashScreen = findViewById(R.id.ll_splash_screen);
        addSplashScreen();
    }

    private void addSplashScreen() {
        Utils.addFragment(this.getSupportFragmentManager(), R.id.ll_splash_screen,new SplashFragment());
        bottomNavigationView.setVisibility(View.GONE);
        mainFragmentContainer.setVisibility(View.GONE);
        someWait();
    }

    private void nextScreen() {
        splashScreen.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        mainFragmentContainer.setVisibility(View.VISIBLE);
        setBottomNavigationItemClickListener();
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void someWait() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                nextScreen();
            }
        }, 3000);
    }


    private void setBottomNavigationItemClickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Utils.addFragment(MainActivity.this.getSupportFragmentManager(), R.id.main_fragment_container, new HomeFragment());
                        break;
                    case R.id.navigation_category:
                        Utils.addFragment(MainActivity.this.getSupportFragmentManager(), R.id.main_fragment_container, new NewCategoryFragment());
                        break;
                }
                return true;
            }
        });
    }
}
