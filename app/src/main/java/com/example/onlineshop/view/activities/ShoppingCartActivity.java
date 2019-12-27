package com.example.onlineshop.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.ShoppingCartFragment;

public class ShoppingCartActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,ShoppingCartActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_shopping_cart_container);
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.activity_shopping_cart_container, ShoppingCartFragment.newInstance() )
                    .commit();
    }


}
