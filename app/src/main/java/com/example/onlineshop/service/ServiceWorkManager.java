package com.example.onlineshop.service;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ServiceWorkManager extends Worker {
    private Context mContext;

    public ServiceWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("ServiceUtils", "in WorkManager");
        ServiceUtils.pollAndNotification(mContext);
        //return Result.retry();
        return Result.success();
    }
}
