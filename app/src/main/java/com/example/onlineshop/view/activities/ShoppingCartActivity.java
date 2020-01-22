package com.example.onlineshop.view.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.ShoppingCartFragment;

public class ShoppingCartActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,ShoppingCartActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ShoppingCartFragment.newInstance();
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public int idRes() {
        return R.id.activity_shopping_cart_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Toolbar toolbar = findViewById(R.id.toolbar_shopping_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("سبد خرید شما");

    }


}
