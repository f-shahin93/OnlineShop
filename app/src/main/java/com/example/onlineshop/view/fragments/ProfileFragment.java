package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentProfileBinding;
import com.example.onlineshop.viewmodel.CustomerViewModel;

public class ProfileFragment extends VisibleFragment {

    private CustomerViewModel mCustomerViewModel;
    private FragmentProfileBinding mBinding;


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        mBinding.tvFirstLastNameProfile.setText(mCustomerViewModel.getCustomerFromDB().getFirstName()
                + " " + mCustomerViewModel.getCustomerFromDB().getLastName());

        mBinding.tvEmailProfile.setText(mCustomerViewModel.getCustomerFromDB().getEmail());

        mBinding.llMyLocationsItemProfile.setOnClickListener(view -> {

        });

        mBinding.llExitItemProfile.setOnClickListener(view -> {
            mCustomerViewModel.exitUserFromApp();
            getActivity().finish();
        });

        return mBinding.getRoot();
    }

}
