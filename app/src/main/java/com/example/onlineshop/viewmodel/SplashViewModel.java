package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlineshop.network.ItemShopFetcher;


public class SplashViewModel extends AndroidViewModel {

    private ItemShopFetcher mShopFetcher;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = ItemShopFetcher.getInstance();
    }

    public void setItemsListsOfHomePage() {
        mShopFetcher.getAllCategory();
        mShopFetcher.getOrderProductList("date");
        mShopFetcher.getOrderProductList("popularity");
        mShopFetcher.getOrderProductList("rating");
    }

}
