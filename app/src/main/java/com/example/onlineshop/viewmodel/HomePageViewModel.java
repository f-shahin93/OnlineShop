package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.repository.ProductRepository;

import java.util.List;

public class HomePageViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mListNewestProMutableLiveData;
    private MutableLiveData<List<Product>> mListPopularProMutableLiveData;
    private MutableLiveData<List<Product>> mListMostPointProMutableLiveData;
    private MutableLiveData<List<CategoriesItem>> mListCategoryMutableLiveData;
    private ItemShopFetcher mShopFetcher;



    public HomePageViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = new ItemShopFetcher();
    }

    public MutableLiveData<List<Product>> getListNewestProMutableLiveData() {
        mListNewestProMutableLiveData = mShopFetcher.getLastProduct("date");
        return mListNewestProMutableLiveData;

    }

    public MutableLiveData<List<Product>> getListPopularProMutableLiveData() {
        mListPopularProMutableLiveData = mShopFetcher.getLastProduct("popularity");
        return mListPopularProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListMostPointProMutableLiveData() {
        mListMostPointProMutableLiveData = mShopFetcher.getLastProduct("rating");
        return mListMostPointProMutableLiveData;
    }

    public MutableLiveData<List<CategoriesItem>> getListCategoryMutableLiveData() {
        mListCategoryMutableLiveData = mShopFetcher.getAllCategory();
        return mListCategoryMutableLiveData;
    }



}
