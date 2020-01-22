package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.prefs.QueryPreferences;
import com.example.onlineshop.service.PollService;
import com.example.onlineshop.service.ServiceWorkManager;
import com.example.onlineshop.view.activities.DisconnectActivity;
import com.example.onlineshop.view.activities.SplashActivity;

import java.util.concurrent.TimeUnit;


public class SplashViewModel extends AndroidViewModel {

    private ItemShopFetcher mShopFetcher;
    private Context mContext;
    private WorkManager mWorkmanager;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = ItemShopFetcher.getInstance();
        mContext = application;
    }

    public void setItemsListsOfHomePage() {
        mShopFetcher.getAllCategory("default", 0);
        mShopFetcher.getOrderProductList("date");
        mShopFetcher.getOrderProductList("popularity");
        mShopFetcher.getOrderProductList("rating");
    }

    public void startServiceNotify() {
        Log.d("ServiceUtils", "in SplashViewModel");

        mWorkmanager = WorkManager.getInstance(mContext);
        int n = QueryPreferences.getStatusNotification(mContext);
        boolean b = QueryPreferences.getStatusWorkManager(mContext);

        if (QueryPreferences.getStatusNotification(mContext) == 0) {
            mWorkmanager.cancelAllWork();
        }

        if (!QueryPreferences.getStatusWorkManager(mContext)) {
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                    .Builder(ServiceWorkManager.class,
                    QueryPreferences.getStatusNotification(mContext),
                    TimeUnit.MINUTES)
                    .build();

            mWorkmanager.enqueueUniquePeriodicWork("work", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
            QueryPreferences.setStatusWorkManager(mContext, true);
        }
    }

    public boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


}
