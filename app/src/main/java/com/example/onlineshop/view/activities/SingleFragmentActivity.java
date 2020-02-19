package com.example.onlineshop.view.activities;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.utils.ShopConstants;
import com.example.onlineshop.viewmodel.ConnectivityViewModel;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    @LayoutRes
    public abstract int layoutRes();

    @IdRes
    public abstract int idRes();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutRes());

        ConnectivityViewModel connectivityViewModel = new ViewModelProvider(this).get(ConnectivityViewModel.class);

        if(!connectivityViewModel.isOnline(this))
            startActivityForResult(DisconnectActivity.newIntent(this), ShopConstants.REQUEST_CODE_DISCONNECT);

        beginTransFrag();
    }

    private void beginTransFrag() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(idRes());
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(idRes(), createFragment())
                    .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED || data == null)
            return;

        if (requestCode == ShopConstants.REQUEST_CODE_DISCONNECT) {
            beginTransFrag();
        }
    }


}
