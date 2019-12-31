package com.example.onlineshop.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.onlineshop.R;
import com.example.onlineshop.viewmodel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashViewModel splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.setItemsListsOfHomePage();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        },3000);

    }
}
