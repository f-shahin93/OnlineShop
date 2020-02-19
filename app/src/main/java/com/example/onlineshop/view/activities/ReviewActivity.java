package com.example.onlineshop.view.activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.ReviewFragment;

public class ReviewActivity extends SingleFragmentActivity {

    public static final String EXTRA_PRODUCT_ID_REVIEW = "Extra productId";
    public static final String EXTRA_PRODUCT_NAME = "Extra product name";

    public static Intent newIntent(Context context, int productid ,String productName) {
        Intent intent = new Intent(context,ReviewActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID_REVIEW,productid);
        intent.putExtra(EXTRA_PRODUCT_NAME,productName);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ReviewFragment.newInstance(getIntent().getStringExtra(EXTRA_PRODUCT_NAME),getIntent().getIntExtra(EXTRA_PRODUCT_ID_REVIEW,0));
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_review;
    }

    @Override
    public int idRes() {
        return R.id.container_review_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
    }


}
