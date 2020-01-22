package com.example.onlineshop.view.fragments;

import androidx.fragment.app.Fragment;
import com.example.onlineshop.R;
import com.example.onlineshop.event.NotificationEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VisibleFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(priority = 2)
    public void onCancelNotification(NotificationEvent event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

}
