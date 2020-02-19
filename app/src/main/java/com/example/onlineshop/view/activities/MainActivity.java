package com.example.onlineshop.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.utils.ShopConstants;
import com.example.onlineshop.view.fragments.MainFragment;
import com.example.onlineshop.viewmodel.CustomerViewModel;
import com.example.onlineshop.viewmodel.DetailProViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private View mHeaderNav;
    private AppCompatTextView mTvAccount, mTvCartCount;
    private AppCompatImageView mIvNavMenu;
    private AppCompatImageButton mIbShoppingCart, mIbSearch;
    private CustomerViewModel mCustomerViewModel;
    private DetailProViewModel mDetailProViewModel;


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

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initToolbar();
        initDrawer();

        mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        mDetailProViewModel = new ViewModelProvider(this).get(DetailProViewModel.class);

        if (!mCustomerViewModel.setUsernameInNavHeader().equals("")) {
            mTvAccount.setText(mCustomerViewModel.setUsernameInNavHeader());
        }
        ShopConstants.getmCustomerNameMLiveData().observe(this, s -> mTvAccount.setText(s));

        mTvAccount.setOnClickListener(view -> startActivity(LoginActivity.newIntent(MainActivity.this)));

        mIbSearch.setOnClickListener(view -> startActivity(SearchActivity.newIntent(MainActivity.this)));
        mIbShoppingCart.setOnClickListener(view -> startActivity(ShoppingCartActivity.newIntent(MainActivity.this)));

        mTvCartCount.setVisibility(View.INVISIBLE);
//        mDetailProViewModel.initCountCart();
//        mDetailProViewModel.getCountProductCart().observe(this, integer -> {
//                    if (integer > 0) {
//                        mTvCartCount.setVisibility(View.VISIBLE);
//                        mTvCartCount.setText(String.valueOf(integer));
//                    }
//                });

    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar_home_page);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mIbSearch = findViewById(R.id.search_item_toolbar_home);
        mIbShoppingCart = findViewById(R.id.shopping_cart_item_toolbar_home);
        mTvCartCount = findViewById(R.id.tv_cart_counter_home);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initDrawer() {
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation);
        mIvNavMenu = findViewById(R.id.iv_ic_nav_menu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
//        toggle.setHomeAsUpIndicator(R.id.iv_ic_nav_menu);

        mIvNavMenu.setOnClickListener(view -> {
            if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                mDrawer.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawer.openDrawer(Gravity.RIGHT);
            }
        });

        // when use icon Drawer
//        mToolbar.setNavigationOnClickListener(v -> {
//            if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
//                mDrawer.closeDrawer(Gravity.RIGHT);
//            } else {
//                mDrawer.openDrawer(Gravity.RIGHT);
//            }
//        });

        mDrawer.setDrawerListener(toggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        mHeaderNav = mNavigationView.getHeaderView(0);
        mTvAccount = mHeaderNav.findViewById(R.id.tv_nav_account);
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
                startActivity(CategoryViewPagerActivity.newIntent(this, ""));
                break;
            }
            case R.id.nav_shopping_cart: {
                startActivity(ShoppingCartActivity.newIntent(this));
                break;
            }
            case R.id.nav_newest_products: {
                startActivity(ProductListSeeAllActivity.newIntent(this, "date"));
                break;
            }
            case R.id.nav_most_viewed_products: {
                startActivity(ProductListSeeAllActivity.newIntent(this, "popularity"));
                break;
            }
            case R.id.nav_most_points_products: {
                startActivity(ProductListSeeAllActivity.newIntent(this, "rating"));
                break;
            }
            case R.id.nav_frequently_questions: {

                break;
            }
            case R.id.nav_setting: {
                startActivity(SettingActivity.newIntent(this));
                break;
            }
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}