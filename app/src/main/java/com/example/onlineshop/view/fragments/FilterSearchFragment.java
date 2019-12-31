package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentFilterSearchBinding;

public class FilterSearchFragment extends Fragment {

    private FragmentFilterSearchBinding mBinding;


    public static Fragment newInstance(){
        FilterSearchFragment fragment = new FilterSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_filter_search, container, false);


        return mBinding.getRoot();
    }

}
