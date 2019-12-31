package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ItemShopFetcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mListProMutableLiveData;
    private ItemShopFetcher mShopFetcher;
    private List<CategoriesItem> mCategoriesItemList;


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = ItemShopFetcher.getInstance();
    }

    public MutableLiveData<List<Product>> getAllproduct() {
        mShopFetcher.getAllProduct();
        return mListProMutableLiveData = mShopFetcher.getAllProductListMutableLiveData();
    }

    public MutableLiveData<List<Product>> getAllproduct(int pageNumber) {
        MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData =  mShopFetcher.getAllProductListByPage(pageNumber);
        return mutableLiveData;
    }

    public String getTotalPageNumber(){
        return mShopFetcher.getTotalPageNumber();
    }

    public List<Product> searchList(List<Product> list ,String mQueryString) {
        List<Product> mListProductFilter = new ArrayList<>();
        List<CategoriesItem> mListCategory = new ArrayList<>();
        for (Product product : list) {

            //search into name product
//            String[] listName = product.getName().split(" ");
//            for (String str : listName) {
//                if (str.equalsIgnoreCase(mQueryString) || mQueryString.contains(str)) {
//                    mListProductFilter.add(product);
//                    mListCategory.addAll(product.getCategories());
//                    break;
//                }
//            }

            //search other feature product
            if (product.getDateCreated().equals(mQueryString) || product.getDescription().equals(mQueryString)
                    || product.getName().contains(mQueryString) || product.getShortDescription().equals(mQueryString)
                    || product.getPrice().equals(mQueryString) || product.getPriceHtml().equals(mQueryString)
                    || product.getRegularPrice().equals(mQueryString) || product.getSalePrice().equals(mQueryString)
                    || product.getPurchaseNote().equals(product) || product.getShippingClass().equals(mQueryString)
                    || product.getStatus().equals(mQueryString) || product.getTaxStatus().equals(mQueryString) || product.getType().equals(mQueryString)
                    || product.getWeight().equals(mQueryString) || product.getDateModified().equals(mQueryString)
                    || String.valueOf(product.getTotalSales()).equals(mQueryString)) {

                mListProductFilter.add(product);
                mListCategory.addAll(product.getCategories());
            }

        }

        for (int i = 0; i < mListCategory.size(); i++) {
            for (int j = i + 1; j < mListCategory.size(); j++) {
                if (mListCategory.get(j).getId() == mListCategory.get(i).getId()) {
                    mListCategory.remove(j);
                }
            }
        }

        setCategoriesItemList(mListCategory);
        return mListProductFilter;
    }


    public List<CategoriesItem> getCategoriesItemList() {
        return mCategoriesItemList;
    }


    public void setCategoriesItemList(List<CategoriesItem> categoriesItemList) {
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
