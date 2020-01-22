package com.example.onlineshop.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class PollService extends IntentService {

    public static final String TAG = "PollService";

    public PollService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("ServiceUtils", "in PollService");

        ServiceUtils.pollAndNotification(this);
    }

}
