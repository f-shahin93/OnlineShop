package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.view.fragments.DetailListCategoryFragment;

import java.util.List;

public class ViewPagerCategViewModel extends AndroidViewModel {

    private MutableLiveData<List<CategoriesItem>> mListCategoryMutableLiveData;
    private MutableLiveData<List<Product>> mListProMutableLiveData;
    private ItemShopFetcher mShopFetcher;
    private String mCategoryName;
    private long mCategoryId;

    public ViewPagerCategViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher =  ItemShopFetcher.getInstance();
    }

    public MutableLiveData<List<CategoriesItem>> getListCategoryMutableLiveData() {
      //  mListCategoryMutableLiveData = mShopFetcher.getAllCategory();
        return mListCategoryMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListProMutableLiveData() {
       // mListProMutableLiveData = mShopFetcher.getProductsSubCategory(mCategoryName,String.valueOf(mCategoryId));
        return mListProMutableLiveData;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(long categoryId) {
        mCategoryId = categoryId;
    }

}