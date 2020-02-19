package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSignupBinding;
import com.example.onlineshop.viewmodel.CustomerViewModel;

public class SignupFragment extends VisibleFragment {

    private CustomerViewModel mCustomerViewModel;
    private FragmentSignupBinding mBinding;
    private SendResultSignUpCallback mResultSignUpCallback;


    public static SignupFragment newInstance(SendResultSignUpCallback callback) {
        SignupFragment fragment = new SignupFragment(callback);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SignupFragment(SendResultSignUpCallback callback) {
        // Required empty public constructor
        mResultSignUpCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);

        mBinding.buttonSignUp.setOnClickListener(view -> {
            if (mBinding.etFirstName.getText().toString().equals("")
                    || mBinding.etLastName.getText().toString().equals("")
                    || mBinding.etUsername.getText().toString().equals("")
                    || mBinding.etPasswordSignUp.getText().toString().equals("")
                    || mBinding.etEmailSignUp.getText().toString().equals("")) {
                Toast.makeText(getContext(), "لطفا جاهای خالی را پر کنید!", Toast.LENGTH_SHORT).show();
            } else {
                mBinding.buttonSignUp.setVisibility(View.GONE);
                mBinding.progressSignUp.setVisibility(View.VISIBLE);
                mBinding.tvUnSuccessfulSignUp.setVisibility(View.INVISIBLE);

                mCustomerViewModel.createCustomer(mBinding.etEmailSignUp.getText().toString(),
                        mBinding.etFirstName.getText().toString(),
                        mBinding.etLastName.getText().toString(),
                        mBinding.etUsername.getText().toString(),
                        mBinding.etPasswordSignUp.getText().toString())
                        .observe(this, customers -> {
                            if (customers != null) {
                                mBinding.tvSuccessfulSignUp.setVisibility(View.VISIBLE);
                                mBinding.progressSignUp.setVisibility(View.GONE);
                                mResultSignUpCallback.resultSignUp(mBinding.etEmailSignUp.getText().toString());
                            }else {
                                mBinding.progressSignUp.setVisibility(View.GONE);
                                mBinding.tvUnSuccessfulSignUp.setVisibility(View.VISIBLE);
                                mBinding.buttonSignUp.setVisibility(View.VISIBLE);
                            }

                        });
            }
        });

        return mBinding.getRoot();
    }

    public interface SendResultSignUpCallback {
        void resultSignUp(String email);
    }

}
