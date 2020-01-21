package com.example.onlineshop.view.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.DetailListCategoryFragment;

public class ProductListSubCategoriesActivity extends SingleFragmentActivity {

    public static final String EXTRA_CATEGORY_NAME = "Extra category name";
    public static final String EXTRA_CATEGORY_ID = "Extra category id";

    @Override
    public Fragment createFragment() {
        return DetailListCategoryFragment
                .newInstance(getIntent().getIntExtra(EXTRA_CATEGORY_ID,0),getIntent().getStringExtra(EXTRA_CATEGORY_NAME));
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_product_list_sub_categories;
    }

    @Override
    public int idRes() {
        return R.id.container_product_list_category;
    }

    public static Intent newIntent(Context context ,int categoryId ,String categoryName){
        Intent intent = new Intent(context,ProductListSubCategoriesActivity.class);
        intent.putExtra(EXTRA_CATEGORY_NAME,categoryName);
        intent.putExtra(EXTRA_CATEGORY_ID,categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list_sub_categories);
        Toolbar mToolbar = findViewById(R.id.toolbar_product_list_category);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_CATEGORY_NAME));

    }
}
