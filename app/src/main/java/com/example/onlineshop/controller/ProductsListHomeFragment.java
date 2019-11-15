package com.example.onlineshop.controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.Adapter.HomePageAdapter;
import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ProductFetcher;

import java.util.ArrayList;
import java.util.List;

public class ProductsListHomeFragment extends Fragment implements ProductFetcher.ProductFetcherCallbacks {

    private RecyclerView mRecyclerView;
    private HomePageAdapter mHomePageAdapter;
    private List<Product> mProductsList = new ArrayList<>();


    public ProductsListHomeFragment() {
        // Required empty public constructor
    }

    public static ProductsListHomeFragment newInstance() {
        ProductsListHomeFragment fragment = new ProductsListHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        updateItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_list_home, container, false);

        initUI(view);
        setupAdapter();


        return view;
    }

    private void initUI(View view) {

        mRecyclerView = view.findViewById(R.id.recycler_view_list_home);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void setupAdapter() {
        if (isAdded()) {
            mHomePageAdapter = new HomePageAdapter(getContext(), mProductsList);
            mRecyclerView.setAdapter(mHomePageAdapter);
        }
    }

    @Override
    public void onResponse(List<Product> items) {
        mProductsList = items;
        setupAdapter();
    }

    private void updateItems() {
        ProductFetcher productFetcher = new ProductFetcher(this);
        productFetcher.getLastProduct();

        setupAdapter();

    }


}
