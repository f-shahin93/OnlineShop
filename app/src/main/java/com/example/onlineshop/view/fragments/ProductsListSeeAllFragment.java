package com.example.onlineshop.view.fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.Adapter.ProductListSubCategoryAdapter;
import com.example.onlineshop.view.activities.EndlessRecyclerViewScrollListener;
import com.example.onlineshop.viewmodel.HomePageViewModel;

import java.util.List;

public class ProductsListSeeAllFragment extends VisibleFragment {

    public static final String ARG_STATUS_LIST = "ARG_STATUS_LIST";
    private RecyclerView mRecyclerView;
    private ProductListSubCategoryAdapter mAdapter;
    private List<Product> mProductList;
    private String mStatusList;
    private HomePageViewModel mHomePageViewModel;
    private EndlessRecyclerViewScrollListener mEndlessRecyclVScrollListener;
    private int mPageNumber = 1;

    public ProductsListSeeAllFragment() {
        // Required empty public constructor
    }

    public static ProductsListSeeAllFragment newInstance(String statusList) {
        ProductsListSeeAllFragment fragment = new ProductsListSeeAllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATUS_LIST, statusList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStatusList = getArguments().getString(ARG_STATUS_LIST);
        }

        mHomePageViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);

        if (mStatusList.equals("date")) {
            mHomePageViewModel.getListNewestProMutableLiveData().observe(this, list -> {
                mProductList = list;
                setupAdapter();
            });

        } else if (mStatusList.equals("popularity")) {
            mHomePageViewModel.getListPopularProMutableLiveData().observe(this, list -> {
                mProductList = list;
                setupAdapter();
            });

        } else if (mStatusList.equals("rating")) {
            mHomePageViewModel.getListMostPointProMutableLiveData().observe(this, list -> {
                mProductList = list;
                setupAdapter();
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_list_category, container, false);

        initUI(view);

        return view;
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_detail_list_category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mEndlessRecyclVScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPageNumber++;
                loadNextDataFromApi(mPageNumber);
                // mEndlessRecyclVScrollListener.resetState();
            }
        };

        mRecyclerView.addOnScrollListener(mEndlessRecyclVScrollListener);

    }

    public void loadNextDataFromApi(int offset) {
        if (mStatusList.equals("date")) {
            mHomePageViewModel.getListNewestProByPageMutableLiveData(mStatusList, offset).observe(this, list -> {
                mProductList.addAll(list);
                setupAdapter();
            });

        } else if (mStatusList.equals("popularity")) {
            mHomePageViewModel.getListPopularProByPageMutableLiveData(mStatusList, offset).observe(this, list -> {
                mProductList.addAll(list);
                setupAdapter();
            });

        } else if (mStatusList.equals("rating")) {
            mHomePageViewModel.getListMostPointProByPageMutableLiveData(mStatusList, offset).observe(this, list -> {
                mProductList.addAll(list);
                setupAdapter();
            });
        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            if (mAdapter == null) {
                mAdapter = new ProductListSubCategoryAdapter(getContext(), mProductList);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setListadapter(mProductList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
