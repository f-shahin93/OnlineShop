package com.example.onlineshop.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ProductFetcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity implements ProductFetcher.ProductFetcherCallbacks {

    private SearchView mSearchView;
    private List<Product> mListProduct = new ArrayList<>();
    private List<Product> mListProductFilter = new ArrayList<>();
    private List<CategoriesItem> mListCategory = new ArrayList<>();
    private List<CategoriesItem> mListCategoryFilter = new ArrayList<>();
    private String mQueryString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_activity_menu, menu);

        mSearchView = (SearchView) menu.findItem(R.id.search_activity_menu_item).getActionView();

        mSearchView.setQueryHint("Search:");
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query != null) {
                    mQueryString = query;
                    ProductFetcher productFetcher = new ProductFetcher(SearchActivity.this);
                    productFetcher.getAllProduct();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return true;
            }
        });


        return true;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    public void onProductResponse(List<Product> productList) {
        mListProduct = productList;
        searchList();

    }

    @Override
    public void onCategoryResponse(List<CategoriesItem> categoryList) {

    }

    public void searchList() {
        mListProductFilter = new ArrayList<>();
        mListCategory = new ArrayList<>();
        for (Product product : mListProduct) {

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_search_list, SearchProductListFragment.newInstance(mListProductFilter, mListCategory))
                .commit();

    }


}
