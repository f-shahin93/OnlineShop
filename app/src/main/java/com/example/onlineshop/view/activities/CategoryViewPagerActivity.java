package com.example.onlineshop.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.view.fragments.DetailListCategoryFragment;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.fragments.SubCategoryFragment;
import com.example.onlineshop.viewmodel.ViewPagerCategViewModel;
import com.google.android.material.tabs.TabLayout;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Categories> mCategoriesList = new ArrayList<>();
    private FragmentStatePagerAdapter mAdapter;
    //private FragmentPagerAdapter mAdapter;
    private Banner mBanner;
    private Toolbar mToolbar;
    private ViewPagerCategViewModel mViewModel;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view_pager);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        mViewPager = findViewById(R.id.activity_viewPager_category);
        mTabLayout = findViewById(R.id.tabLayout_category);
        mToolbar = findViewById(R.id.toolbar_categories);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle("دسته بندی محصولات");
        mViewPager.setOffscreenPageLimit(0);

        //mViewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
       // mTabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        mViewModel = ViewModelProviders.of(this).get(ViewPagerCategViewModel.class);

        mViewModel.getListCategoryMutableLiveData().observe(this, categoriesItemList -> {
            mCategoriesList = categoriesItemList;
            CategoryViewPagerActivity.this.setupTabLayout();
        });

        mTabLayout.setupWithViewPager(mViewPager);
        setupTabLayout();


        /*LinearLayoutCompat linearLayout = findViewById(R.id.layout_root_view_pager_activity);
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
        }*/


      /*  mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
            return SubCategoryFragment
                        .newInstance(mCategoriesList.get(position).getId());
                //String tabName = mTabLayout.getTabAt(position).getText().toString();
               // return DetailListCategoryFragment.newInstance(
                     //   mCategoriesList.get(position).getId(),mCategoriesList.get(position).getName());
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
                mViewPager.setCurrentItem(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/

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
                return SubCategoryFragment
                        .newInstance(mCategoriesList.get(position).getId());
//                return DetailListCategoryFragment.newInstance(
//                        mCategoriesList.get(position).getId(),mCategoriesList.get(position).getName());
                //return mViewModel.setFragment(mCategoriesList.get(position));
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
                //mViewPager.setCurrentItem(position);
                //mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CategoryViewPagerActivity.class);
        return intent;
    }

//    public boolean isOnline(Context context) {
//        try {
//            ConnectivityManager connectivityManager =
//                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//            //checking null when airplane mode
//            return (netInfo != null && netInfo.isConnected());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


}
