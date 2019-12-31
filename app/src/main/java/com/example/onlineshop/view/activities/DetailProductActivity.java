package com.example.onlineshop.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ToolbarCustomBinding;
import com.example.onlineshop.view.fragments.DetailProductFragment;
import com.example.onlineshop.model.Product;

public class DetailProductActivity extends SingleFragmentActivity {

    public static final String EXTRA_PRODUCT_ID = "Extra product id";
    //public static Product mProductDetail;

    public static Intent newIntent(Context context, int productId) {
        //mProductDetail = product;
        Intent intent = new Intent(context, DetailProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return DetailProductFragment.newInstance(getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0));
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_detail_product;
    }

    @Override
    public int idRes() {
        return R.id.container_detail_pro;
    }

}
