package com.example.onlineshop.controller.activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.onlineshop.controller.fragments.DetailProductFragment;
import com.example.onlineshop.model.Product;

public class DetailProductActivity extends SingleFragmentActivity {

    public static final String EXTRA_PRODUCT = "Extra product";
    public static Product mProductDetail;

    public static Intent newIntent(Context context , Product product) {
        mProductDetail = product;
        Intent intent =new Intent(context , DetailProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return DetailProductFragment.newInstance(mProductDetail);
    }

}
