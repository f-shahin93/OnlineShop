package com.example.onlineshop.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragments.MainFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;


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

        mToolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation);

        /*Typeface.createFromAsset(mToolbar.getChildAt(0).getContext().getAssets(),"");

        fun Toolbar.changeToolbarFont(){
            for (i in 0 until childCount) {
                val view = getChildAt(i)
                if (view is TextView && view.text == title) {
                    view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/customFont")
                    break
                }
            }
        }*/

        init();


        /*mTvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        //  LinearLayout linearLayout = findViewById(R.id.layout_banner_home_page);
       /* mBanner = new Banner.Builder(this)
                .setParent(linearLayout)
                .setIcon(R.drawable.ic_wifi)
                .setMessage("You have lost connection to the Internet. This app is offline.")
                .setLeftButton("Dismiss", new BannerInterface.OnClickListener() {
                    @Override
                    public void onClick(BannerInterface banner) {
                        mBanner.dismiss();
                    }
                })
                .setRightButton("Turn on wifi", new BannerInterface.OnClickListener() {
                    @Override
                    public void onClick(BannerInterface banner) {
                        if (isOnline(MainActivity.this)) {
                            mBanner.setVisibility(View.GONE);
                            mBanner.dismiss();
                            recreate();
                        } else {
                            mBanner.show();
                            mBanner.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .create();

        if (isOnline(this)) {
            mBanner.setVisibility(View.GONE);
            mBanner.dismiss();
            //recreate();
        } else {
            mSliderPic.setVisibility(View.INVISIBLE);
            mBanner.show();
            mBanner.setVisibility(View.VISIBLE);

        }*/


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawer.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawer.openDrawer(Gravity.RIGHT);
                }
            }

        });

        mDrawer.setDrawerListener(toggle);
        mNavigationView.setNavigationItemSelectedListener(this);
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
                Intent intent = CategoryViewPagerActivity.newIntent(this);
                startActivity(intent);
                break;
            }
            case R.id.nav_shopping_cart: {
                Intent intent = ShoppingCartActivity.newIntent(this);
                startActivity(intent);
                break;
            }
            case R.id.nav_newest_products: {
                Intent intent = ProductListSeeAllActivity.newIntent(this,"date");
                startActivity(intent);
                break;
            }
            case R.id.nav_most_viewed_products: {
                Intent intent = ProductListSeeAllActivity.newIntent(this,"popularity");
                startActivity(intent);
                break;
            }
            case R.id.nav_most_points_products: {
                Intent intent = ProductListSeeAllActivity.newIntent(this,"rating");
                startActivity(intent);
                break;
            }
            case R.id.nav_frequently_questions: {

                break;
            }
            case R.id.nav_setting: {

                break;
            }
            case R.id.tv_nav_account: {
                LoginActivity.newIntent(this);
                break;
            }

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


  /*
    public boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            //checking null when airplane mode
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        // mSearchView = (SearchView) menu.findItem(R.id.search_menu_item).getActionView();

//        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(SearchActivity.newIntent(MainActivity.this));
//                mSearchView.setFocusable(false);
//                mSearchView.onActionViewCollapsed();
//
//            }
//        });

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