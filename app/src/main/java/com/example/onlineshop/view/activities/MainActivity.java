package com.example.onlineshop.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.MainFragment;
import com.google.android.material.navigation.NavigationView;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private View headerNav;
    private TextView mTvAccount;


    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public int idRes() {
        return R.id.container_frag_home_p;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initToolbar();
        initDrawer();

        mTvAccount.setOnClickListener(view -> startActivity(LoginActivity.newIntent(MainActivity.this)));

    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("فروشگاه آنلاین");
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initDrawer() {
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mToolbar.setNavigationOnClickListener(v -> {
            if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                mDrawer.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawer.openDrawer(Gravity.RIGHT);
            }
        });

        mDrawer.setDrawerListener(toggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        headerNav = mNavigationView.getHeaderView(0);
        mTvAccount = headerNav.findViewById(R.id.tv_nav_account);
        toggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home_page: {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_frag_home_p, MainFragment.newInstance())
                        .commit();
                break;
            }
            case R.id.nav_list_category: {
                startActivity(CategoryViewPagerActivity.newIntent(this,""));
                break;
            }
            case R.id.nav_shopping_cart: {
                startActivity(ShoppingCartActivity.newIntent(this));
                break;
            }
            case R.id.nav_newest_products: {
                startActivity(ProductListSeeAllActivity.newIntent(this,"date"));
                break;
            }
            case R.id.nav_most_viewed_products: {
                startActivity(ProductListSeeAllActivity.newIntent(this,"popularity"));
                break;
            }
            case R.id.nav_most_points_products: {
                startActivity(ProductListSeeAllActivity.newIntent(this,"rating"));
                break;
            }
            case R.id.nav_frequently_questions: {

                break;
            }
            case R.id.nav_setting: {

                break;
            }
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.shopping_cart_main_menu_item: {
                startActivity(ShoppingCartActivity.newIntent(this));
                break;
            }
            case R.id.search_menu_item: {
                startActivity(SearchActivity.newIntent(MainActivity.this));
            }

        }
        return true;
    }
}