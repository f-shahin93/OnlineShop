package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.customers.Billing;
import com.example.onlineshop.model.customers.Customers;
import com.example.onlineshop.model.customers.Shipping;
import com.example.onlineshop.network.ItemShopFetcher;

public class CustomerViewModel extends AndroidViewModel {

    private Customers mCustomer;
    private ItemShopFetcher mShopFetcher;


    public CustomerViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = ItemShopFetcher.getInstance();
    }

    public MutableLiveData<Customers> createCustomer(String email, String firstName, String lastName, String username, String password){
        mCustomer = new Customers(email,firstName,lastName,username,
                new Billing("","","","","","",lastName,"", "",firstName,email),
                new Shipping("","","","","",lastName,"","",firstName));
        mShopFetcher.setCustomer(mCustomer);
        return mShopFetcher.getCustomerLiveData();
    }
}
