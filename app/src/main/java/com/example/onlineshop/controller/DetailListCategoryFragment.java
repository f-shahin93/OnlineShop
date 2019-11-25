package com.example.onlineshop.controller;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.Adapter.HomePageAdapter;
import com.example.onlineshop.R;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ProductFetcher;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailListCategoryFragment extends Fragment implements ProductFetcher.ProductFetcherCallbacks {

    public static final String Detail_List_Category_Fragment = "DetailListCategoryFragment";
    public static final String ARG_CATEGORY_NAME = "Arg categoryName";
    public static final String ARG_CATEGORY_ID = "Arg categoryId";
    private RecyclerView mRecyclerView;
    private HomePageAdapter mAdapter;
    private List<Product> mProductList = new ArrayList<>();
    private String mCategoryName;
    private long mCategoryId ;


    public DetailListCategoryFragment() {
        // Required empty public constructor
    }

    public static DetailListCategoryFragment newInstance(long categoryId , String categoryName) {
        DetailListCategoryFragment fragment = new DetailListCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_NAME,categoryName);
        args.putLong(ARG_CATEGORY_ID,categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mCategoryName = getArguments().getString(ARG_CATEGORY_NAME);
            mCategoryId = getArguments().getLong(ARG_CATEGORY_ID);
        }

        updateItem();
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

    public void setupAdapter(){
        if(isAdded()){
            mAdapter = new HomePageAdapter(getContext(),mProductList ,Detail_List_Category_Fragment);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void updateItem() {
        ProductFetcher productFetcher = new ProductFetcher(this);
        productFetcher.getProductsSubCategory(mCategoryName,String.valueOf(mCategoryId));
    }

    @Override
    public void onProductResponse(List<Product> productList) {
        mProductList = productList;
        setupAdapter();
    }

    @Override
    public void onCategoryResponse(List<CategoriesItem> categoryList) {

    }
}
