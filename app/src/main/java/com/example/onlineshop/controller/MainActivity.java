package com.example.onlineshop.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.onlineshop.Adapter.CategoryAdapter;
import com.example.onlineshop.R;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ProductFetcher;
import com.google.android.material.navigation.NavigationView;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , ProductFetcher.ProductFetcherCallbacks {

    public static final String HOME_PAGE_VIEW_PAGER_CATEGORY = "homePageViewPagerCategory";
    private SliderLayout mSliderPic;
    private LinearLayout mLinearLayoutLatestProducts;
    private LinearLayout mLinearLayoutMostViewedProducts;
    private LinearLayout mLinearLayoutMostPointsProducts;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private RecyclerView mRecyclerViewCategory;
    private CategoryAdapter mCategoryAdapter;
    private List mCategoryList = new ArrayList();
    private SearchView mSearchView;
    private Banner mBanner;

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
        mRecyclerViewCategory = findViewById(R.id.recycler_view_category_homePage);

        LinearLayout linearLayout = findViewById(R.id.layout_banner_home_page);
        mBanner = new Banner.Builder(this)
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

        }

        ProductFetcher productFetcher = new ProductFetcher(this);
        productFetcher.getAllCategory();

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


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCategory.setLayoutManager(layoutManager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_products_list_home);
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.linearLayout_newest_products, ProductsListHomeFragment.newInstance("date"))
                    .commit();

        FragmentManager fragmentManager2 = getSupportFragmentManager();
        Fragment fragment2 = fragmentManager2.findFragmentById(R.id.fragment_products_list_home);
        if (fragment2 == null)
            fragmentManager2
                    .beginTransaction()
                    .add(R.id.linearLayout_most_viewed_products, ProductsListHomeFragment.newInstance("popularity"))
                    .commit();

        FragmentManager fragmentManager3 = getSupportFragmentManager();
        Fragment fragment3 = fragmentManager3.findFragmentById(R.id.fragment_products_list_home);
        if (fragment3 == null)
            fragmentManager3
                    .beginTransaction()
                    .add(R.id.linearLayout_most_points_products, ProductsListHomeFragment.newInstance("rating"))
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


    @Override
    public void onProductResponse(List<Product> productList) {

    }

    @Override
    public void onCategoryResponse(List<CategoriesItem> categoryList) {
        mCategoryList = categoryList;
        setupAdapter();
    }

    private void setupAdapter() {
        mCategoryAdapter = new CategoryAdapter(this, mCategoryList, HOME_PAGE_VIEW_PAGER_CATEGORY);
        mRecyclerViewCategory.setAdapter(mCategoryAdapter);
    }

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        mSearchView = (SearchView) menu.findItem(R.id.search_menu_item).getActionView();

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity.newIntent(MainActivity.this));
                mSearchView.setFocusable(false);
                mSearchView.onActionViewCollapsed();

            }
        });

        return true;
    }


}