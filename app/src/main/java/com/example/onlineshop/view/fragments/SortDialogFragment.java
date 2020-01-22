package com.example.onlineshop.view.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.event.NotificationEvent;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SortDialogFragment extends DialogFragment {

    public static final String ARG_STATUS_LIST = "Arg statusList";
    private MaterialRadioButton mRbMostSales, mRbPriceMuchToLow, mRbPriceLowToMuch, mRbNewest;
    private MaterialRadioButton[] mRbList;
    private ResultDialogCallback mCallback;
    private String mStatusCurrentList;
    private String mResult;


    public SortDialogFragment(ResultDialogCallback callback) {
        // Required empty public constructor
        mCallback = callback;
    }

    public static SortDialogFragment newInstance(ResultDialogCallback callback, String statusList) {
        SortDialogFragment fragment = new SortDialogFragment(callback);
        Bundle args = new Bundle();
        args.putString(ARG_STATUS_LIST, statusList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStatusCurrentList = getArguments().getString(ARG_STATUS_LIST);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_sort_dialog, null, false);

        mRbMostSales = view.findViewById(R.id.rb_most_sales_dialog_sort);
        mRbPriceMuchToLow = view.findViewById(R.id.rb_price_muchToLow_dialog_sort);
        mRbPriceLowToMuch = view.findViewById(R.id.rb_price_lowToMuch_dialog_sort);
        mRbNewest = view.findViewById(R.id.rb_newest_dialog_sort);

        mRbList = new MaterialRadioButton[]{mRbMostSales, mRbPriceLowToMuch, mRbPriceMuchToLow, mRbNewest};

        for (MaterialRadioButton radioButton : mRbList) {
            if (radioButton.getText().equals(mStatusCurrentList)) {
                radioButton.setChecked(true);
                break;
            }
        }

        mRbMostSales.setOnClickListener(getListener(mRbMostSales.getText().toString()));
        mRbPriceMuchToLow.setOnClickListener(getListener(mRbPriceMuchToLow.getText().toString()));
        mRbPriceLowToMuch.setOnClickListener(getListener(mRbPriceLowToMuch.getText().toString()));
        mRbNewest.setOnClickListener(getListener(mRbNewest.getText().toString()));


        return new AlertDialog.Builder(getContext())
                .setView(view)
                .create();

    }

    private View.OnClickListener getListener(final String itemSelect) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.getResultDialog(itemSelect);
                //setResult(itemSelect);
                SortDialogFragment.this.dismiss();
            }
        };
    }

    public interface ResultDialogCallback {
        void getResultDialog(String sortList);
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

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
