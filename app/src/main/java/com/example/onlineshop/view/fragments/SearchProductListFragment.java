package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.view.Adapter.CategoryAdapter;
import com.example.onlineshop.view.Adapter.HomePageAdapter;
import com.example.onlineshop.view.Adapter.ProductListSubCategoryAdapter;

import java.io.Serializable;
import java.util.List;

public class SearchProductListFragment extends VisibleFragment {

    public static final String ARG_CATEGORIES_LIST = "Arg categories List";
    public static final String SEARCH_PRODUCT_LIST_FRAGMENT = "SearchProductListFragment";
    private List<Product> mProductList;
    private List<Categories> mCategoriesList;
    private RecyclerView mRecyclerViewProductList,mRecyclerViewCategoriesList ;
    private ProductListSubCategoryAdapter mProductListSubCategoryAdapter;
    private CategoryAdapter mCategoryAdapter;


    public static final String ARG_PRODUCT_LIST = "Arg productlist";

    public SearchProductListFragment() {
        // Required empty public constructor
    }

    public static SearchProductListFragment newInstance(List<Product> productList, List<Categories> categoriesList) {
        SearchProductListFragment fragment = new SearchProductListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_LIST, (Serializable) productList);
        args.putSerializable(ARG_CATEGORIES_LIST, (Serializable) categoriesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductList = (List<Product>) getArguments().getSerializable(ARG_PRODUCT_LIST);
            mCategoriesList = (List<Categories>) getArguments().getSerializable(ARG_CATEGORIES_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_product_list, container, false);

        mRecyclerViewProductList = view.findViewById(R.id.recycler_view_search_product_list_Fragment);
        mRecyclerViewCategoriesList = view.findViewById(R.id.recycler_view_search_categories_list_fragment);

        mRecyclerViewProductList.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCategoriesList.setLayoutManager(layoutManager);

        mProductListSubCategoryAdapter = new ProductListSubCategoryAdapter(getContext(), mProductList);
        mRecyclerViewProductList.setAdapter(mProductListSubCategoryAdapter);

        mCategoryAdapter = new CategoryAdapter(getContext(), mCategoriesList, SEARCH_PRODUCT_LIST_FRAGMENT);
        mRecyclerViewCategoriesList.setAdapter(mCategoryAdapter);

        return view;
    }

}
