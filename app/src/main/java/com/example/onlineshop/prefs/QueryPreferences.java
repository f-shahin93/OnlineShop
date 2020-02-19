package com.example.onlineshop.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {

    private static final String PREF_LAST_PRODUCT_ID = "lastProductId";
    private static final String PREF_STATUS_NOTIFICATION = "statusNotification";
    private static final String PREF_STATUS_WORKMANAGER = "statusWorkManager";
    private static final String PREF_STATUS_LOGIN = "statusLogin";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE);
    }

    public static String getLastProductId(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getString(PREF_LAST_PRODUCT_ID,null);
    }

    public static void setLastProductId(Context context,String lastProductId){
        SharedPreferences prefs = getSharedPreferences(context);
        prefs.edit().putString(PREF_LAST_PRODUCT_ID,lastProductId).apply();
    }

    public static int getStatusNotification(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getInt(PREF_STATUS_NOTIFICATION,15);
    }

    public static void setStatusNotification(Context context,int statusNotification){
        SharedPreferences prefs = getSharedPreferences(context);
        prefs.edit().putInt(PREF_STATUS_NOTIFICATION,statusNotification).apply();
    }

    public static boolean getStatusWorkManager(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getBoolean(PREF_STATUS_WORKMANAGER,false);
    }

    public static void setStatusWorkManager(Context context,boolean statusWorkManager){
        SharedPreferences prefs = getSharedPreferences(context);
        prefs.edit().putBoolean(PREF_STATUS_WORKMANAGER,statusWorkManager).apply();
    }

    public static boolean getStatusLogin(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.getBoolean(PREF_STATUS_LOGIN,false);
    }

    public static void setStatusLogin(Context context,boolean statusLogin){
        SharedPreferences prefs = getSharedPreferences(context);
        prefs.edit().putBoolean(PREF_STATUS_LOGIN,statusLogin).apply();
    }



}
