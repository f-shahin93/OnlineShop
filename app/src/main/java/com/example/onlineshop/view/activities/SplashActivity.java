package com.example.onlineshop.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.event.NotificationEvent;
import com.example.onlineshop.viewmodel.SplashViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SplashActivity extends AppCompatActivity {

    private SplashViewModel splashViewModel;
    private ProgressBar mProgressBar;

   /* BroadcastReceiver mybroadcast = new BroadcastReceiver() {

        //When Event is published, onReceive method is called
        @Override
        public void onReceive(Context context, Intent intent) {

            if (splashViewModel.isOnline(context)) {
                Log.d("TagProduct", "Connected : " + intent.getAction());
                Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(context, "Connection Failed! ", Toast.LENGTH_LONG).show();
                Log.d("TagProduct", "Connection Failed! : " + intent.getAction());
            }

        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Log.d("TagProduct", "call oncreate splash");
        mProgressBar = findViewById(R.id.progressBar__splash_activity);

        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.setItemsListsOfHomePage();
        splashViewModel.startServiceNotify();


//        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE" );
//        registerReceiver(mybroadcast ,intentFilter);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 5000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TagProduct", "call onResume splash");

//        if(splashViewModel.isOnline(SplashActivity.this)){
//            splashViewModel.setItemsListsOfHomePage();
//
//            new Handler().postDelayed(() -> {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }, 5000);
//
//        }else {
//            mProgressBar.setVisibility(View.VISIBLE);
////            Intent intent = new Intent(this, DisconnectActivity.class);
////            startActivity(intent);
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        Log.d("TagProduct", "call onStart splash");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        Log.d("TagProduct", "call onStop splash");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TagProduct", "call onDestroy splash");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TagProduct", "call onPause splash");
    }

    @Subscribe(priority = 2)
    public void onCancelNotification(NotificationEvent event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }
}
