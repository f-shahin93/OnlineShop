package com.example.onlineshop.view.activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return LoginFragment.newInstance();
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_single_fragment;
    }

    @Override
    public int idRes() {
        return R.id.fragment_container;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        return intent;
    }
}
