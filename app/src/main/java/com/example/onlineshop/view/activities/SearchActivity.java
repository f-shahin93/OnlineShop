package com.example.onlineshop.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivitySearchBinding;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.view.fragments.SearchProductListFragment;
import com.example.onlineshop.view.fragments.SortDialogFragment;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SortDialogFragment.ResultDialogCallback {

    public static final String TAG_SORT_DIALOG_FRAGMENT = "SortDialogFragment";
    private SearchView mSearchView;
    private List<Product> mListProductFilter;
    private List<CategoriesItem> mListCategory ;
    private String mQueryString;
    private SortDialogFragment mSortDialogFragment;
    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mBinding;
    private String mTagDialogFragment;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        setSupportActionBar(mBinding.toolbar);

        mSortDialogFragment = SortDialogFragment.newInstance(this, mBinding.tvHintSortItemSearch.getText().toString());

        mBinding.setSearchViewModel(mSearchViewModel);

        mBinding.llSortingActivitySearch.setOnClickListener(view -> {
            mTagDialogFragment = mSortDialogFragment.getTag();
            mSortDialogFragment.show(getSupportFragmentManager(), TAG_SORT_DIALOG_FRAGMENT);
        });

        mBinding.llFilteringActivitySearch.setOnClickListener(view -> {

        });

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

                    mSearchViewModel.getAllproduct().observe(SearchActivity.this, list -> {
                        mListProductFilter = mSearchViewModel.searchList(list, mQueryString);
                        mListCategory = mSearchViewModel.getCategoriesItemList();
                        callFragment();
                    });
                }
                mSearchView.setFocusable(false);
               // mSearchView.onActionViewCollapsed();
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

    private void callFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_search_list, SearchProductListFragment.newInstance(mListProductFilter, mListCategory))
                .commit();
    }


    @Override
    public void getResultDialog(String sortList) {

        mBinding.tvHintSortItemSearch.setText(sortList);

        mListProductFilter = mSearchViewModel.getResultDialog(sortList, mListProductFilter);
        callFragment();

    }


//    public void searchList() {
//        mListProductFilter = new ArrayList<>();
//        mListCategory = new ArrayList<>();
//        for (Product product : mListProduct) {
//
//            //search into name product
//            String[] listName = product.getName().split(" ");
//            for (String str : listName) {
//                if (str.equalsIgnoreCase(mQueryString) || mQueryString.contains(str)) {
//                    mListProductFilter.add(product);
//                    mListCategory.addAll(product.getCategories());
//                    break;
//                }
//            }
//
//            //search other feature product
//            if (product.getDateCreated().equals(mQueryString) || product.getDescription().equals(mQueryString)
//                    || product.getName().contains(mQueryString)
//                    || product.getPrice().equals(mQueryString) || product.getPriceHtml().equals(mQueryString)
//                    || product.getRegularPrice().equals(mQueryString) || product.getSalePrice().equals(mQueryString)
//                    || product.getPurchaseNote().equals(product) || product.getShippingClass().equals(mQueryString)
//                    || product.getShortDescription().equals(mQueryString) || product.getSku().equals(mQueryString)
//                    || product.getStatus().equals(mQueryString) || product.getTaxClass().equals(mQueryString)
//                    || product.getTaxStatus().equals(mQueryString) || product.getType().equals(mQueryString)
//                    || product.getWeight().equals(mQueryString) || product.getDateModified().equals(mQueryString)
//                    || String.valueOf(product.getTotalSales()).equals(mQueryString)) {
//
//                mListProductFilter.add(product);
//                mListCategory.addAll(product.getCategories());
//            }
//
//        }
//
//        for (int i = 0; i < mListCategory.size(); i++) {
//            for (int j = i + 1; j < mListCategory.size(); j++) {
//                if (mListCategory.get(j).getId() == mListCategory.get(i).getId()) {
//                    mListCategory.remove(j);
//                }
//            }
//
//        }
//
//        callFragment();
//
//    }

}