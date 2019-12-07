package com.example.onlineshop.controller.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.controller.fragments.DetailListCategoryFragment;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ProductFetcher;
import com.google.android.material.tabs.TabLayout;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewPagerActivity extends AppCompatActivity implements ProductFetcher.ProductFetcherCallbacks {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<CategoriesItem> mCategoriesList = new ArrayList<>();
    private FragmentStatePagerAdapter mAdapter;
    private Banner mBanner;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view_pager);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        mViewPager = findViewById(R.id.activity_viewPager_category);
        mTabLayout = findViewById(R.id.tabLayout_category);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        //mViewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        LinearLayoutCompat linearLayout = findViewById(R.id.layout_root_view_pager_activity);
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
                        if (isOnline(CategoryViewPagerActivity.this)) {
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
        } else {
            mBanner.show();
            mBanner.setVisibility(View.VISIBLE);
        }

        ProductFetcher productFetcher = new ProductFetcher(this);
        productFetcher.getAllCategory();

        mTabLayout.setupWithViewPager(mViewPager);

        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                //String tabName = mTabLayout.getTabAt(position).getText().toString();
                return DetailListCategoryFragment.newInstance(
                        mCategoriesList.get(position).getId(),mCategoriesList.get(position).getName());
            }

            @Override
            public int getCount() {
                return mCategoriesList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mCategoriesList.get(position).getName();
            }
        };
        //mViewPager.setLayoutDirection();
        mViewPager.setAdapter(mAdapter);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // mTabLayout.setScrollPosition(position ,positionOffset,false);
            }

            @Override
            public void onPageSelected(int position) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    public void setupTabLayout(){

        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mCategoriesList.get(position).getName();
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                //String tabName = mTabLayout.getTabAt(position).getText().toString();
                return DetailListCategoryFragment.newInstance(
                        mCategoriesList.get(position).getId(),mCategoriesList.get(position).getName());
            }

            @Override
            public int getCount() {
                return mCategoriesList.size();
            }
        };

        mViewPager.setAdapter(mAdapter);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }


    @Override
    public void onProductResponse(List<Product> productList) {

    }

    @Override
    public void onCategoryResponse(List<CategoriesItem> categoryList) {
        mCategoriesList = categoryList;
        setupTabLayout();
    }

    @Override
    public void onCustomerResponse(boolean singupCustomer) {

    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CategoryViewPagerActivity.class);
        return intent;
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


}
