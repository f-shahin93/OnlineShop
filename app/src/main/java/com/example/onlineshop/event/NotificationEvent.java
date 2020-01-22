package com.example.onlineshop.event;

import android.app.Notification;

public class NotificationEvent {

    private Notification mNotification;
    private int mNotificationId;

    public Notification getNotification() {
        return mNotification;
    }

    public void setNotification(Notification notification) {
        mNotification = notification;
    }

    public int getNotificationId() {
        return mNotificationId;
    }

    public void setNotificationId(int notificationId) {
        mNotificationId = notificationId;
    }

    public NotificationEvent(Notification notification, int notificationId) {
        mNotification = notification;
        mNotificationId = notificationId;
    }

}
