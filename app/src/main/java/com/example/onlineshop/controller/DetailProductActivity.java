package com.example.onlineshop.controller;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.example.onlineshop.model.Product;

import java.io.Serializable;

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
