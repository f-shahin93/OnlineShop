package com.example.onlineshop.view.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.LoginFragment;
import com.example.onlineshop.view.fragments.ProfileFragment;
import com.example.onlineshop.view.fragments.SignupFragment;
import com.example.onlineshop.viewmodel.CustomerViewModel;

public class LoginActivity extends SingleFragmentActivity implements LoginFragment.StartFragCallback
        , SignupFragment.SendResultSignUpCallback {

    @Override
    public Fragment createFragment() {
        CustomerViewModel mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        if (mCustomerViewModel.isUser()) {
            return ProfileFragment.newInstance();
        }
        else {
            return LoginFragment.newInstance(this, "");
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_single_fragment;
    }

    @Override
    public int idRes() {
        return R.id.fragment_container;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void startSignUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(idRes(), SignupFragment.newInstance(this))
                .commit();
    }


    @Override
    public void resultSignUp(String email) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(idRes(), LoginFragment.newInstance(this, email))
                .commit();
    }
}
