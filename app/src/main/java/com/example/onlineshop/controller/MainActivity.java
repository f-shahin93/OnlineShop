package com.example.onlineshop.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.onlineshop.R;
import com.example.onlineshop.network.ProductFetcher;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SliderLayout mSliderPic;
    private LinearLayout mLinearLayoutLatestProducts;
    private LinearLayout mLinearLayoutMostViewedProducts;
    private LinearLayout mLinearLayoutMostPointsProducts;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        mSliderPic = findViewById(R.id.slider_picApp);
        mNavigationView = findViewById(R.id.navigation);
        mLinearLayoutLatestProducts = findViewById(R.id.linearLayout_newest_products);
        mLinearLayoutMostViewedProducts = findViewById(R.id.linearLayout_most_viewed_products);
        mLinearLayoutMostPointsProducts = findViewById(R.id.linearLayout_most_points_products);


        init();
        setupSlider();


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


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_products_list_home);
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.linearLayout_newest_products, ProductsListHomeFragment.newInstance())
                    .commit();

        FragmentManager fragmentManager2 = getSupportFragmentManager();
        Fragment fragment2 = fragmentManager2.findFragmentById(R.id.fragment_products_list_home);
        if (fragment2 == null)
            fragmentManager2
                    .beginTransaction()
                    .add(R.id.linearLayout_most_viewed_products, ProductsListHomeFragment.newInstance())
                    .commit();

        FragmentManager fragmentManager3 = getSupportFragmentManager();
        Fragment fragment3 = fragmentManager3.findFragmentById(R.id.fragment_products_list_home);
        if (fragment3 == null)
            fragmentManager3
                    .beginTransaction()
                    .add(R.id.linearLayout_most_points_products, ProductsListHomeFragment.newInstance())
                    .commit();
    }

    private void setupSlider() {
        HashMap<String, String> url_maps = new HashMap<>();
        url_maps.put("لوازم برقی", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012855.jpg");
        url_maps.put("صوتی و تصویری", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012860.jpg");
        url_maps.put("مراقبت از پوست", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012909.jpg");
        url_maps.put("لذت از لحظات", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000013192.jpg");


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSliderPic.addSlider(textSliderView);
        }
        mSliderPic.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderPic.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderPic.setDuration(5000);
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

                break;
            }
            case R.id.nav_list_category: {
                break;
            }
            case R.id.nav_shopping_cart: {
                break;
            }
            case R.id.nav_newest_products: {
                break;
            }
            case R.id.nav_most_viewed_products: {
                break;
            }
            case R.id.nav_most_points_products: {
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


}