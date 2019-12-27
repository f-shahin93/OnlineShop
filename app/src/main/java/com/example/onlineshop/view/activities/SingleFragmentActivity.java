package com.example.onlineshop.view.activities;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.onlineshop.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    //public Toolbar mToolbar;
    public abstract Fragment createFragment();

    @LayoutRes
    public abstract int layoutRes();

    @IdRes
    public abstract int idRes();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutRes());
        //mToolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(mToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(idRes());
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(idRes(), createFragment())
                    .commit();
    }
}
