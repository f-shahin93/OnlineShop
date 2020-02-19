package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.R;
import com.example.onlineshop.model.User;
import com.example.onlineshop.model.customers.Billing;
import com.example.onlineshop.model.customers.Customers;
import com.example.onlineshop.model.customers.Shipping;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.prefs.QueryPreferences;
import com.example.onlineshop.repository.UserRepository;
import com.example.onlineshop.utils.ShopConstants;
import com.example.onlineshop.view.activities.MainActivity;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    private Customers mCustomer;
    private ItemShopFetcher mShopFetcher;
    private UserRepository mUserRepository;
    private Context mContext;


    public CustomerViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = ItemShopFetcher.getInstance();
        mUserRepository = UserRepository.getInstance(application);
        mContext = application;
    }

    public MutableLiveData<Customers> createCustomer(String email, String firstName, String lastName, String username, String password) {
        mCustomer = new Customers(email, firstName, lastName, username, password,
                new Billing("", "", "", "", "", "", lastName, "", "", firstName, email),
                new Shipping("", "", "", "", "", lastName, "", "", firstName));
        mShopFetcher.setCustomer(mCustomer);
        return mShopFetcher.getCustomerLiveData();
    }

    public MutableLiveData<List<Customers>> getCustomers(String email) {
        return mShopFetcher.getCustomer(email);
    }

    public void insertCustomerInDB(Customers customer) {
        mUserRepository.insertCustomer(customer);
    }

    public User getCustomerFromDB() {
        return mUserRepository.getUser();
    }

    public void deleteCustomer() {
        mUserRepository.deleteCustomer();
    }

    public String setUsernameInNavHeader() {
        User user = getCustomerFromDB();
        if (user != null) {
            return user.getFirstName() + " " + user.getLastName();
        } else
            return "";
    }

    public void prepareUserInApp(Customers customer) {
        insertCustomerInDB(customer);
        QueryPreferences.setStatusLogin(mContext, true);
        ShopConstants.getmCustomerNameMLiveData().setValue(customer.getFirstName() + " " + customer.getLastName());
    }

    public void exitUserFromApp() {
        deleteCustomer();
        QueryPreferences.setStatusLogin(mContext,false);
        ShopConstants.getmCustomerNameMLiveData().setValue("ثبت نام/ورود");
    }

    public boolean isUser() {
        return QueryPreferences.getStatusLogin(mContext);
    }

}
