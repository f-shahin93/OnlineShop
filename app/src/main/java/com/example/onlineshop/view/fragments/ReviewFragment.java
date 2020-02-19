package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentReviewBinding;
import com.example.onlineshop.model.review.Review;
import com.example.onlineshop.view.Adapter.ReviewAdapter;
import com.example.onlineshop.viewmodel.DetailProViewModel;

import java.util.List;

public class ReviewFragment extends VisibleFragment {

    public static final String ARGS_PRODUCT_NAME = "Args product name";
    private FragmentReviewBinding mReviewBinding;
    private DetailProViewModel mDetailProViewModel;
    public static final String ARGS_PRODUCT_ID = "Args productId";
    private int mProductId;
    private String mProductName;
    private List<Review> mReviewList;
    private ReviewAdapter mReviewAdapter;

    public static ReviewFragment newInstance(String productName,int productid) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_PRODUCT_ID, productid);
        args.putString(ARGS_PRODUCT_NAME,productName);
        fragment.setArguments(args);
        return fragment;
    }


    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductId = getArguments().getInt(ARGS_PRODUCT_ID);
            mProductName = getArguments().getString(ARGS_PRODUCT_NAME);
        }

        mDetailProViewModel = new ViewModelProvider(this).get(DetailProViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mReviewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false);

        init();
        mDetailProViewModel.getProductReviewsListLiveData(mProductId).observe(this, list -> {
            mReviewList = list;
            setupAdapter();

        });

        return mReviewBinding.getRoot();
    }

    private void init() {
        mReviewBinding.recyclerViewReviewProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        mReviewBinding.tvNameReviewProduct.setText(mProductName);
    }

    private void setupAdapter(){
        mReviewAdapter = new ReviewAdapter(getContext(),mReviewList);
        mReviewBinding.recyclerViewReviewProduct.setAdapter(mReviewAdapter);
    }

}
