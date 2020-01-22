package com.example.onlineshop.view.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.SettingFragment;

public class SettingActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context,SettingActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return SettingFragment.newInstance();
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    public int idRes() {
        return R.id.container_setting_frag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
    }
}
