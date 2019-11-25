package com.example.onlineshop.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onlineshop.R;

public class DisconnectActivity extends AppCompatActivity {

    private AppCompatTextView mTextView;
    private Button mButtonTry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discconnect);

        mButtonTry = findViewById(R.id.button_disconnect);

        mButtonTry.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                boolean result = BroadcastRecFragment.newInstance().isOnline(DisconnectActivity.this);

                if (result) {

                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();


                    //onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "لطفا مجددا تلاش کنید!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
