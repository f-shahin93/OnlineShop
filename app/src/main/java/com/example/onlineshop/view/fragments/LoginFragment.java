package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLoginBinding;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.customers.Customers;
import com.example.onlineshop.viewmodel.CustomerViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LoginFragment extends VisibleFragment {

    private CustomerViewModel mCustomerViewModel;
    private FragmentLoginBinding mBinding;


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        mBinding.buttonSignUp.setOnClickListener(view -> {
            mCustomerViewModel.createCustomer(mBinding.etEmail.getText().toString(),
                    mBinding.etFirstName.getText().toString(),
                    mBinding.etLastName.getText().toString(),
                    mBinding.etUsername.getText().toString(),
                    mBinding.etPassword.getText().toString())
                    .observe(this, customers ->
                            Toast.makeText(getContext()
                                    , "شما با موفقیت ثبت نام شدید"
                                    , Toast.LENGTH_LONG).show());
        });

        return mBinding.getRoot();
    }

//    @Override
//    public void onCustomerResponse(boolean singupCustomer) {
//        if (singupCustomer) {
//            Toast.makeText(getContext(), "تبریک!شما ثبت نام شدید.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "متاسفانه ثبت نام نشدید!دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
//        }
//
//    }

}
