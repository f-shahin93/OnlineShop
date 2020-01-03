package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.repository.ProductRepository;

import java.util.List;

public class HomePageViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mListNewestProMutableLiveData;
    private MutableLiveData<List<Product>> mListPopularProMutableLiveData;
    private MutableLiveData<List<Product>> mListMostPointProMutableLiveData;
    private MutableLiveData<List<Categories>> mListCategoryMutableLiveData;
    private ItemShopFetcher mShopFetcher;

    public HomePageViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher =  ItemShopFetcher.getInstance();
    }

    public MutableLiveData<List<Product>> getListNewestProMutableLiveData() {
        mListNewestProMutableLiveData = mShopFetcher.getListNewestProMutableLiveData();
        return mListNewestProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListPopularProMutableLiveData() {
        mListPopularProMutableLiveData = mShopFetcher.getListPopularProMutableLiveData();
        return mListPopularProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListMostPointProMutableLiveData() {
        mListMostPointProMutableLiveData = mShopFetcher.getListMostPointProMutableLiveData();
        return mListMostPointProMutableLiveData;
    }

    public MutableLiveData<List<Categories>> getListCategoryMutableLiveData() {
        mListCategoryMutableLiveData = mShopFetcher.getListCategoryLiveData();
        return mListCategoryMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListNewestProByPageMutableLiveData(String status,int pageNumber) {
        MutableLiveData<List<Product>> mLisProMutableLiveData;
        mLisProMutableLiveData = mShopFetcher.getOrderProductListByPage(status,pageNumber);
        return mLisProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListPopularProByPageMutableLiveData(String status,int pageNumber) {
        MutableLiveData<List<Product>> mLisProMutableLiveData;
        mLisProMutableLiveData = mShopFetcher.getOrderProductListByPage(status,pageNumber);
        return mLisProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListMostPointProByPageMutableLiveData(String status,int pageNumber) {
        MutableLiveData<List<Product>> mLisProMutableLiveData;
        mLisProMutableLiveData= mShopFetcher.getOrderProductListByPage(status,pageNumber);
        return mLisProMutableLiveData;
    }

}
