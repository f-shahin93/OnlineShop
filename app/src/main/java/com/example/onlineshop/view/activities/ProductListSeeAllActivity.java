package com.example.onlineshop.view.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.ProductsListSeeAllFragment;

public class ProductListSeeAllActivity extends SingleFragmentActivity {

    public static final String EXTRA_STATUS_LIST = "Extra statusList";
    private Toolbar mToolbar;


    @Override
    public Fragment createFragment() {
        return ProductsListSeeAllFragment.newInstance(getIntent().getStringExtra(EXTRA_STATUS_LIST));
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_product_list_see_all;
    }

    @Override
    public int idRes() {
        return R.id.container__product_list_see_all;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_see_all);

        mToolbar = findViewById(R.id.toolbar_product_list_see_all);
        setSupportActionBar(mToolbar);

        String statusList = getIntent().getStringExtra(EXTRA_STATUS_LIST);

        if(statusList.equals("date")){
            mToolbar.setTitle("جدیدترین محصولات");
        }else if(statusList.equals("popularity")){
            mToolbar.setTitle("پربازدیدترین محصولات");
        }else if(statusList.equals("rating")){
            mToolbar.setTitle("بهترین محصولات");
        }

    }

    public static Intent newIntent(Context context,String statusList){
        Intent intent = new Intent(context,ProductListSeeAllActivity.class);
        intent.putExtra(EXTRA_STATUS_LIST,statusList);
        return intent;
    }
}
