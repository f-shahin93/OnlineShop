package com.example.onlineshop.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.onlineshop.R;
import com.example.onlineshop.event.NotificationEvent;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.prefs.QueryPreferences;
import com.example.onlineshop.view.activities.DetailProductActivity;
import com.example.onlineshop.view.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class ServiceUtils {

    public static final String TAG = "ServiceUtils";
    public static final int REQUEST_CODE_NOTIFICATION = 1;
    public static final int REQUEST_CODE_NOTIFICATION_TEST = 2;
    public static final int NOTIFICATION_ID = 0;
    public static final int NOTIFICATION_ID_TEST = 1;

    public static void pollAndNotification(Context context) {

        if (!isNetworkAvailableAndConnected(context))
            return;

        String lastProductId = QueryPreferences.getLastProductId(context);
        ItemShopFetcher itemShopFetcher = ItemShopFetcher.getInstance();
        List<Product> productList = null;
        try {
            productList = itemShopFetcher.getProductListSync();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        if (productList == null || productList.size() == 0)
            return;

        String resultId = String.valueOf(productList.get(0).getId());
        if (!resultId.equals(lastProductId) && lastProductId != null) {
            //send notification
            Log.d(TAG, "new product on wooCommerce");

            createAndSendNotification(context, productList.get(0).getId());
        } else {
            Log.d(TAG, "nothing happened");
            createAndSendNotificationforTestNothing(context, productList.get(0).getId());
        }

        QueryPreferences.setLastProductId(context, resultId);

    }

    private static boolean isNetworkAvailableAndConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isAvailable = manager.getActiveNetworkInfo() != null;
        boolean isConnected = isAvailable && manager.getActiveNetworkInfo().isConnected();

        return isConnected;
    }

    private static void createAndSendNotification(Context context, int productId) {
        Intent activityIntent = DetailProductActivity.newIntent(context, productId);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                REQUEST_CODE_NOTIFICATION,
                activityIntent,
                0);

        String channelId = context.getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("محصول جدید")
                .setContentText("محصول جدید افزوده شد.لطفا دیدن فرمایید.")
                .setSmallIcon(R.drawable.ic_back_online_shop)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        EventBus.getDefault().post(new NotificationEvent(notification, NOTIFICATION_ID));

    }

    private static void createAndSendNotificationforTestNothing(Context context, int productId) {
        Intent activityIntent = DetailProductActivity.newIntent(context, productId);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                REQUEST_CODE_NOTIFICATION_TEST,
                activityIntent,
                0);

        String channelId = context.getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("تست نوتیفیکیشن")
                .setContentText("چطورییی؟! :))")
                .setSmallIcon(R.drawable.ic_back_online_shop)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        EventBus.getDefault().post(new NotificationEvent(notification, NOTIFICATION_ID_TEST));
    }

}