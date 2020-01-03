package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.network.ItemShopFetcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mListProMutableLiveData;
    private ItemShopFetcher mShopFetcher;
    private List<Categories> mCategoriesItemList = new ArrayList<>();


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = ItemShopFetcher.getInstance();
    }

    public MutableLiveData<List<Product>> getAllproduct() {
        mShopFetcher.getAllProduct();
        return mListProMutableLiveData = mShopFetcher.getAllProductListMutableLiveData();
    }


    public MutableLiveData<List<Product>> search(String querySearch){
        mShopFetcher.getProductListSearch(querySearch);
        return mListProMutableLiveData = mShopFetcher.getProductListSearchMLiveData();

    }


    public List<Categories> getCategoriesItemList() {
        return mCategoriesItemList;
    }

    public void setCategoryList(List<Product> list) {

        for(Product product : list){
            mCategoriesItemList.addAll(product.getCategories());
        }

        for (int i = 0; i < mCategoriesItemList.size(); i++) {
            for (int j = i + 1; j < mCategoriesItemList.size(); j++) {
                if (mCategoriesItemList.get(j).getId() == mCategoriesItemList.get(i).getId()) {
                    mCategoriesItemList.remove(j);
                }
            }
        }

    }

    public void setCategoriesItemList(List<Categories> categoriesItemList) {
        mCategoriesItemList = categoriesItemList;
    }


    public List<Product> getResultDialog(String sortList , List<Product> listProductFilter) {
        List<Product> mListProductFilter = listProductFilter;
        switch (sortList) {
            case "پرفروش ترین": {
                Collections.sort(mListProductFilter, (product, p1) ->
                        Integer.valueOf(product.getTotalSales()).compareTo(p1.getTotalSales()));
                break;
            }
            case "قیمت از زیاد به کم": {
                Collections.sort(mListProductFilter, Collections.reverseOrder((product, t1) ->
                        Integer.valueOf(product.getPrice()).compareTo(Integer.valueOf(t1.getPrice()))));
                break;
            }
            case "قیمت از کم به زیاد": {
                Collections.sort(mListProductFilter, (product, p1) ->
                        Integer.valueOf(product.getPrice()).compareTo(Integer.valueOf(p1.getPrice())));
                break;
            }
            case "جدیدترین": {
                Collections.sort(mListProductFilter, (product, p1) ->
                        (product.getDateModified()).compareTo(p1.getDateModified()));
                break;
            }
        }
        return mListProductFilter;
    }

}
