package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.databinding.ActivitySearchBinding;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ItemShopFetcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mListProMutableLiveData;
    private MutableLiveData<List<CategoriesItem>> mListCategoryMutableLiveData;
    private ItemShopFetcher mShopFetcher;
    private List<CategoriesItem> mCategoriesItemList;
    private MutableLiveData<Boolean> mIsClickSortingLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsClickFilteringLiveData = new MutableLiveData<>();
    private String mHintSort;


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mShopFetcher = new ItemShopFetcher();
    }

    public MutableLiveData<List<Product>> getAllproduct() {
        mListProMutableLiveData = mShopFetcher.getAllProduct();
        return mListProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListProMutableLiveData() {
        return mListProMutableLiveData;
    }

    public void setListProMutableLiveData(MutableLiveData<List<Product>> listProMutableLiveData) {
        mListProMutableLiveData = listProMutableLiveData;
    }

    public List<Product> searchList(List<Product> list ,String mQueryString) {
        List<Product> mListProductFilter = new ArrayList<>();
        List<CategoriesItem> mListCategory = new ArrayList<>();
        for (Product product : list) {

            //search into name product
            String[] listName = product.getName().split(" ");
            for (String str : listName) {
                if (str.equalsIgnoreCase(mQueryString) || mQueryString.contains(str)) {
                    mListProductFilter.add(product);
                    mListCategory.addAll(product.getCategories());
                    break;
                }
            }

            //search other feature product
            if (product.getDateCreated().equals(mQueryString) || product.getDescription().equals(mQueryString)
                    || product.getName().contains(mQueryString)
                    || product.getPrice().equals(mQueryString) || product.getPriceHtml().equals(mQueryString)
                    || product.getRegularPrice().equals(mQueryString) || product.getSalePrice().equals(mQueryString)
                    || product.getPurchaseNote().equals(product) || product.getShippingClass().equals(mQueryString)
                    || product.getShortDescription().equals(mQueryString) || product.getSku().equals(mQueryString)
                    || product.getStatus().equals(mQueryString) || product.getTaxClass().equals(mQueryString)
                    || product.getTaxStatus().equals(mQueryString) || product.getType().equals(mQueryString)
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


    public MutableLiveData<Boolean> getIsClickSortingLiveData() {
        return mIsClickSortingLiveData;
    }

    public void setIsClickSortingLiveData() {
        mIsClickSortingLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> getIsClickFilteringLiveData() {
        return mIsClickFilteringLiveData;
    }

    public void setIsClickFilteringLiveData() {
        mIsClickFilteringLiveData.setValue(true);
    }

    public void setHintSort(String hintSort){
        mHintSort = hintSort;
    }

    public String getHintSort(){
        return mHintSort;
    }

    public List<Product> getResultDialog(String sortList , List<Product> listProductFilter) {
        setHintSort(sortList);
        List<Product> mListProductFilter = listProductFilter;
        //mBinding.tvHintSortItemSearch.setText(sortList);
        switch (sortList) {
            case "پرفروش ترین": {
                Collections.sort(mListProductFilter, new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product p1) {
                        return Integer.valueOf(product.getTotalSales()).compareTo(p1.getTotalSales());
                    }
                });
                //return mListProductFilter;
                break;
            }
            case "قیمت از زیاد به کم": {
                Collections.sort(mListProductFilter, Collections.reverseOrder(new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        return Integer.valueOf(product.getPrice()).compareTo(Integer.valueOf(t1.getPrice()));
                    }
                }));
                //return mListProductFilter;
                break;
            }
            case "قیمت از کم به زیاد": {
                Collections.sort(mListProductFilter, new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product p1) {
                        return Integer.valueOf(product.getPrice()).compareTo(Integer.valueOf(p1.getPrice()));
                    }
                });
                //return mListProductFilter;
                break;
            }
            case "جدیدترین": {
                Collections.sort(mListProductFilter, new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product p1) {
                        return (product.getDateModified()).compareTo(p1.getDateModified());
                    }
                });
                //return mListProductFilter;
                break;
            }
        }
        return mListProductFilter;
    }

}
