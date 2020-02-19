package com.example.onlineshop.utils;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ShopConstants {

    private List<Integer> countProductSpinner = new ArrayList<>();
    private static MutableLiveData<String> mCustomerNameMLiveData = new MutableLiveData<>();
    public static final int REQUEST_CODE_DISCONNECT = 1;


    {
        countProductSpinner = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            countProductSpinner.add(i);
        }
    }

    public static MutableLiveData<String> getmCustomerNameMLiveData() {
        return mCustomerNameMLiveData;
    }

}
