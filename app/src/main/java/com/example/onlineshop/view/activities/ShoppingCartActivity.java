package com.example.onlineshop.view.activities;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityShoppingCartBinding;
import com.example.onlineshop.view.fragments.ShoppingCartFragment;
import com.example.onlineshop.viewmodel.DetailProViewModel;

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

        ActivityShoppingCartBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_shopping_cart);
        DetailProViewModel viewModel = new ViewModelProvider(this).get(DetailProViewModel.class);
        setSupportActionBar(binding.toolbarShoppingCart);

        viewModel.getCountProductCart().observe(this, integer ->
                binding.tvCartCounterToolbar.setText(String.valueOf(integer)));

    }


}
