package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLoginBinding;
import com.example.onlineshop.prefs.QueryPreferences;
import com.example.onlineshop.utils.ShopConstants;
import com.example.onlineshop.viewmodel.CustomerViewModel;

public class LoginFragment extends VisibleFragment {

    public static final String ARGS_EMAIL = "Args email";
    private CustomerViewModel mCustomerViewModel;
    private FragmentLoginBinding mBinding;
    private StartFragCallback mStartFragCallback;
    private String email = "";


    public static LoginFragment newInstance(StartFragCallback callback, String email) {
        LoginFragment fragment = new LoginFragment(callback);
        Bundle args = new Bundle();
        args.putString(ARGS_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment(StartFragCallback callback) {
        mStartFragCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            email = getArguments().getString(ARGS_EMAIL);
        }

        mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        if (!email.equals(""))
            mBinding.etEmailLogin.setText(email);

        mBinding.buttonLogin.setOnClickListener(view -> {
            if(mBinding.etEmailLogin.getText().equals("") || mBinding.etPasswordLogin.getText().equals("")){
                Toast.makeText(getContext(), "لطفا ایمیل یا رمز عبور را وارد نمایید!", Toast.LENGTH_SHORT).show();
            }else {
                mCustomerViewModel.getCustomers(mBinding.etEmailLogin.getText().toString()).observe(this
                        , customers -> {
                            if (customers.size() == 0)
                                Toast.makeText(getContext(), "ایمیل نامعتبر است یا ثبت نام کنید!", Toast.LENGTH_SHORT).show();
                            else {
                                mCustomerViewModel.prepareUserInApp(customers.get(0));
                                getActivity().finish();
                            }
                        });
            }
        });

        mBinding.buttonLoginSignUp.setOnClickListener(view -> mStartFragCallback.startSignUp());

        return mBinding.getRoot();
    }

    public interface StartFragCallback {
        void startSignUp();
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
