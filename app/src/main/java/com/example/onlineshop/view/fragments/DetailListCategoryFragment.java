package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.Adapter.ProductListSubCategoryAdapter;
import com.example.onlineshop.viewmodel.ViewPagerCategViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailListCategoryFragment extends Fragment {

    public static final String ARG_CATEGORY_NAME = "Arg categoryName";
    public static final String ARG_CATEGORY_ID = "Arg categoryId";
    private RecyclerView mRecyclerView;
    private ProductListSubCategoryAdapter mAdapter;
    private List<Product> mProductList = new ArrayList<>();
    private ViewPagerCategViewModel mViewModel;


    public DetailListCategoryFragment() {
        // Required empty public constructor
    }

    public static DetailListCategoryFragment newInstance() {
        DetailListCategoryFragment fragment = new DetailListCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mCategoryName = getArguments().getString(ARG_CATEGORY_NAME);
//            mCategoryId = getArguments().getLong(ARG_CATEGORY_ID);
//        }

        mViewModel = ViewModelProviders.of(this).get(ViewPagerCategViewModel.class);
        
        mViewModel.getListProMutableLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                mProductList = list;
                setupAdapter();
            }
        });

        //updateItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_list_category, container, false);

        init(view);

        return view;
    }


    private void init(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_detail_list_category);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void setupAdapter() {
        if (isAdded()) {
            mAdapter = new ProductListSubCategoryAdapter(getContext(), mProductList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

//    private void updateItem() {
//        ItemShopFetcher productFetcher = new ItemShopFetcher(this);
//        productFetcher.getProductsSubCategory(mCategoryName,String.valueOf(mCategoryId));
//    }

//    @Override
//    public void onProductResponse(List<Product> productList) {
//        mProductList = productList;
//        setupAdapter();
//    }
//
//    @Override
//    public void onCategoryResponse(List<CategoriesItem> categoryList) {
//
//    }
//
//    @Override
//    public void onCustomerResponse(boolean singupCustomer) {
//
//    }
}
