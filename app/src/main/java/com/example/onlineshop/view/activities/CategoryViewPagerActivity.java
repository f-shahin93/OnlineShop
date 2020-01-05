package com.example.onlineshop.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.onlineshop.R;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.fragments.SubCategoryFragment;
import com.example.onlineshop.viewmodel.ViewPagerCategViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryViewPagerActivity extends AppCompatActivity {

    public static final String EXTRA_ID_CATEGORY = "Extra idCategory";
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private List<Categories> mCategoriesList = new ArrayList<>();
    private FragmentStateAdapter mAdapter;
    private Banner mBanner;
    private Toolbar mToolbar;
    private ViewPagerCategViewModel mViewModel;
    private String mIdCategory;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view_pager);

        mIdCategory = getIntent().getStringExtra(EXTRA_ID_CATEGORY);

        mViewModel = ViewModelProviders.of(this).get(ViewPagerCategViewModel.class);
        mCategoriesList = mViewModel.getBasicCategories();

        initUi();
        setupToolbar();
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


    }

    private void initUi() {
        mViewPager = findViewById(R.id.activity_viewPager_category);
        mTabLayout = findViewById(R.id.tabLayout_category);

        for (int i = 0; i < mCategoriesList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mCategoriesList.get(i).getName()));
        }

    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar_categories);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("دسته بندی محصولات");
    }

    public void setupTabLayout() {
        mAdapter = new FragmentStateAdapter(this) {

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return SubCategoryFragment
                        .newInstance(mCategoriesList.get(position).getId());
            }

            @Override
            public int getItemCount() {
                return mCategoriesList.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) ->
                tab.setText(mCategoriesList.get(position).getName())).attach();

        if (!mIdCategory.equals("")) {
            int num = Integer.parseInt(mIdCategory);
            int position = 0;
            for (int i = 0; i < mCategoriesList.size(); i++) {
                if (mCategoriesList.get(i).getId() == num) {
                    position = i;
                    break;
                }
            }
            mTabLayout.setScrollPosition(position, 0, true);
            mViewPager.setCurrentItem(position);
        }

    }

    public static Intent newIntent(Context context, String idCategory) {
        Intent intent = new Intent(context, CategoryViewPagerActivity.class);
        intent.putExtra(EXTRA_ID_CATEGORY, idCategory);
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
