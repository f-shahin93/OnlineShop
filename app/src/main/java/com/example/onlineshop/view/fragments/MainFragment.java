package com.example.onlineshop.view.fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.HomePageLayoutBinding;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.Adapter.CategoryAdapter;
import com.example.onlineshop.view.Adapter.HomePageAdapter;
import com.example.onlineshop.viewmodel.HomePageViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends Fragment {

    private CategoryAdapter mCategoryAdapter;
    private HomePageAdapter mHomePageAdapter;
    private HomePageLayoutBinding mBinding;
    private HomePageViewModel mHomePageViewModel;

    public static Fragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePageViewModel = ViewModelProviders.of(this).get(HomePageViewModel.class);

        mHomePageViewModel.getListCategoryMutableLiveData().observe(this, new Observer<List<CategoriesItem>>() {
            @Override
            public void onChanged(List<CategoriesItem> categoriesItemList) {
                mCategoryAdapter = new CategoryAdapter(getContext(),categoriesItemList);
                mBinding.recyclerViewCategoryHomePage.setAdapter(mCategoryAdapter);
            }
        });

        mHomePageViewModel.getListNewestProMutableLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                MainFragment.this.setupProductAdapter(list, mBinding.recViewNewestProductsListHome);
            }
        });

        mHomePageViewModel.getListPopularProMutableLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                MainFragment.this.setupProductAdapter(list, mBinding.recViewMostViewedProductsListHome);
            }
        });

        mHomePageViewModel.getListMostPointProMutableLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                MainFragment.this.setupProductAdapter(list, mBinding.recViewMostPointsProductsListHome);
            }
        });

    }

    private void setupProductAdapter(List<Product> list, RecyclerView recyclerView) {
        mHomePageAdapter = new HomePageAdapter(getContext(), list);
        recyclerView.setAdapter(mHomePageAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.home_page_layout, container, false);

        setupSlider();
        init();

        //return inflater.inflate(R.layout.fragment_main, container, false);
        return mBinding.getRoot();
    }

    private void init() {

        mBinding.recViewNewestProductsListHome
                .setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mBinding.recViewMostViewedProductsListHome
                .setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mBinding.recViewMostPointsProductsListHome
                .setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mBinding.recyclerViewCategoryHomePage
                .setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupSlider() {

        HashMap<String, String> url_maps = new HashMap<>();
        url_maps.put("لوازم برقی", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012855.jpg");
        url_maps.put("صوتی و تصویری", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012860.jpg");
        url_maps.put("مراقبت از پوست", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000012909.jpg");
        url_maps.put("لذت از لحظات", "https://bucket-15.digicloud-oss.com/digikala-adservice-banners/1000013192.jpg");


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mBinding.sliderPicApp.addSlider(textSliderView);
        }
        mBinding.sliderPicApp.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mBinding.sliderPicApp.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinding.sliderPicApp.setDuration(5000);
    }

}
