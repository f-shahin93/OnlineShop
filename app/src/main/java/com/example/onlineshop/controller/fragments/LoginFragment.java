package com.example.onlineshop.controller.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.customers.Customers;
import com.example.onlineshop.network.ProductFetcher;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LoginFragment extends Fragment implements ProductFetcher.ProductFetcherCallbacks {

    private MaterialButton mButtonSignUp;
    private EditText mEtUsername, mEtPassword;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mButtonSignUp = view.findViewById(R.id.button_signUp);
        mEtUsername = view.findViewById(R.id.et_username);
        mEtPassword = view.findViewById(R.id.et_password);

        Customers customers = new Customers();
        customers.setEmail(mEtUsername.getText().toString());

        ProductFetcher productFetcher = new ProductFetcher(this);
        productFetcher.setCustomer(customers);

        return view;
    }

    @Override
    public void onProductResponse(List<Product> productList) {

    }

    @Override
    public void onCategoryResponse(List<CategoriesItem> categoryList) {

    }

    @Override
    public void onCustomerResponse(boolean singupCustomer) {
        if (singupCustomer) {
            Toast.makeText(getContext(), "تبریک!شما ثبت نام شدید.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "متاسفانه ثبت نام نشدید!دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
        }

    }
}
