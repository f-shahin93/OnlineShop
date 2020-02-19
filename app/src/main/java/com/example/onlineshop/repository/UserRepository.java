package com.example.onlineshop.repository;

import android.content.Context;

import com.example.onlineshop.model.User;
import com.example.onlineshop.model.customers.Billing;
import com.example.onlineshop.model.customers.Customers;
import com.example.onlineshop.model.customers.Shipping;
import com.example.onlineshop.network.ItemShopFetcher;

import io.realm.Realm;
import io.realm.RealmList;

public class UserRepository {

    private static UserRepository instance;
    private Realm mRealm;
    private ItemShopFetcher mShopFetcher;
    private Context mContext;


    private UserRepository(Context context) {
        mRealm = Realm.getDefaultInstance();
        mShopFetcher = ItemShopFetcher.getInstance();
        mContext = context;
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public boolean isUser(Customers customer) {
        if (!mRealm.isInTransaction())
            mRealm.beginTransaction();

        for (User userTemp : mRealm.where(User.class).findAll()) {
            if (userTemp.getIdUser() == customer.getId())
                return true;
        }
        return false;
    }

    public void insertCustomer(Customers customer) {
        if (!mRealm.isInTransaction())
            mRealm.beginTransaction();

        if (!isUser(customer)) {
            User user = mRealm.createObject(User.class, String.valueOf(customer.getId()));
            Shipping shipping = mRealm.createObject(Shipping.class);
            Billing billing = mRealm.createObject(Billing.class);
            user.setIdUser(customer.getId());
            user.setEmail(customer.getEmail());
            user.setFirstName(customer.getFirstName());
            user.setLastName(customer.getLastName());
            user.setUsername(customer.getUsername());

            shipping.setFirstName(customer.getFirstName());
            shipping.setLastName(customer.getLastName());

            billing.setEmail(customer.getEmail());
            billing.setFirstName(customer.getFirstName());
            billing.setLastName(customer.getLastName());

            user.setShipping(shipping);
            user.setBilling(billing);
        }
        mRealm.commitTransaction();

    }

    public User getUser() {
        User user = null;
        mRealm.beginTransaction();
        for (User userTemp : mRealm.where(User.class).findAll())
            user = userTemp;
        mRealm.commitTransaction();
        return user;
    }

    public void deleteCustomer() {
        mRealm.beginTransaction();
        for (User user : mRealm.where(User.class).findAll())
            user.deleteFromRealm();
        mRealm.commitTransaction();
    }
}