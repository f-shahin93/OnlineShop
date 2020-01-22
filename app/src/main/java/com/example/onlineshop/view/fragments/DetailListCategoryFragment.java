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
import com.example.onlineshop.view.activities.EndlessRecyclerViewScrollListener;
import com.example.onlineshop.viewmodel.SearchViewModel;
import com.example.onlineshop.viewmodel.ViewPagerCategViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailListCategoryFragment extends VisibleFragment {

    private static final String ARG_CATEGORY_NAME = "Arg categoryName";
    private static final String ARG_CATEGORY_ID = "Arg categoryId";
    private RecyclerView mRecyclerView;
    private ProductListSubCategoryAdapter mAdapter;
    private List<Product> mProductList = new ArrayList<>();
    private ViewPagerCategViewModel mViewModel;
    private String mCategoryName;
    private int mCategoryId;
    private EndlessRecyclerViewScrollListener mEndlessRecyclVScrollListener;
    private int mPageNumber = 1;


    public DetailListCategoryFragment() {
        // Required empty public constructor
    }

    public static DetailListCategoryFragment newInstance(int categoryId, String categoryName) {
        DetailListCategoryFragment fragment = new DetailListCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_NAME, categoryName);
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryName = getArguments().getString(ARG_CATEGORY_NAME);
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }

        mViewModel = ViewModelProviders.of(this).get(ViewPagerCategViewModel.class);

        mViewModel.getListProMutableLiveData(mCategoryName, mCategoryId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                mProductList = list;
                setupAdapter();
            }
        });

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

    private void loadNextDataFromApi(int offset) {
        mViewModel.getListProByPageMutableLiveData(mCategoryName,mCategoryId,offset).observe(this, list -> {
            mProductList.addAll(list);
            setupAdapter();
        });
    }

    private void setupAdapter() {
        if (isAdded()) {
            mAdapter = new ProductListSubCategoryAdapter(getContext(), mProductList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
