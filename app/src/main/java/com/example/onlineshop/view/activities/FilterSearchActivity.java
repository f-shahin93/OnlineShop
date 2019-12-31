package com.example.onlineshop.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.FilterSearchFragment;

public class FilterSearchActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return FilterSearchFragment.newInstance();
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_filter_search;
    }

    @Override
    public int idRes() {
        return R.id.container_filter_search;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context ,FilterSearchActivity.class);
        return intent;
    }
}
