package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.view.fragments.DetailListCategoryFragment;

import java.util.List;
import java.util.Observable;

public class ViewPagerCategViewModel extends AndroidViewModel {

    private MutableLiveData<List<Categories>> mListCategoryMutableLiveData;
    private MutableLiveData<List<Categories>> mListSubCategoryMutableLiveData;
    private MutableLiveData<List<Product>> mListProMutableLiveData;
    private ItemShopFetcher mShopFetcher;
    private Context mContext;


    public ViewPagerCategViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
        mShopFetcher =  ItemShopFetcher.getInstance();
    }

    public MutableLiveData<List<Categories>> getListCategoryMutableLiveData() {
        mListCategoryMutableLiveData = mShopFetcher.getListCategoryLiveData();
        return mListCategoryMutableLiveData;
    }

    public MutableLiveData<List<Categories>> getSubCategoriesMutableLiveData(String displayCatg , int parentCateg){
        mShopFetcher.getAllCategory(displayCatg,parentCateg);
        mListSubCategoryMutableLiveData = mShopFetcher.getListSubCategoryLiveData();
        return mListSubCategoryMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListProMutableLiveData(String categoryName,int categoryId) {
        mShopFetcher.getProductsSubCategory(categoryName,String.valueOf(categoryId));
        mListProMutableLiveData = mShopFetcher.getProductListSubCategMLiveData();
        return mListProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListProByPageMutableLiveData(String namecateg,int idCateg,int pageNumber) {
        MutableLiveData<List<Product>> mLisProMutableLiveData;
        mLisProMutableLiveData = mShopFetcher.getProductsSubCategoryByPage(namecateg,String.valueOf(idCateg),pageNumber);
        return mLisProMutableLiveData;
    }

}