package com.example.onlineshop.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshop.R;


public class BroadcastRecFragment extends Fragment {

    public static final String ARG_TAG_PAGE_NAME = "Arg tagPageName";
    private boolean mIsNetworkAvailable;
    /*BroadcastReceiver mybroadcast = new BroadcastReceiver() {

        //When Event is published, onReceive method is called
        @Override
        public void onReceive(Context context, Intent intent) {

            if (isOnline()) {
                Log.d("TagProduct", "Connected : " + intent.getAction());
                Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(context, "Connection Failed! ", Toast.LENGTH_LONG).show();
                Log.d("TagProduct", "Connection Failed! : " + intent.getAction());
            }

        }
    };*/

    public BroadcastRecFragment() {
        // Required empty public constructor
    }

    public static BroadcastRecFragment newInstance() {
        BroadcastRecFragment fragment = new BroadcastRecFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            // باید نال را هم بررسی کنید چون در حالت پرواز نال خواهد بود
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        if(isOnline(getContext())){

        }else {
            Intent intent = new Intent(this.getContext(),DisconnectActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(),"hiiiiiii",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }


}
