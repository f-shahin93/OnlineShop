package com.example.onlineshop.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.onlineshop.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SliderLayout mSliderPic;
    private LinearLayout mLinearLayoutLatestProducts;
    private LinearLayout mLinearLayoutMostViewedProducts;
    private LinearLayout mLinearLayoutMostPointsProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSliderPic = findViewById(R.id.slider_picApp);
        mLinearLayoutLatestProducts = findViewById(R.id.linearLayout_newest_products);
        mLinearLayoutMostViewedProducts = findViewById(R.id.linearLayout_most_viewed_products);
        mLinearLayoutMostPointsProducts = findViewById(R.id.linearLayout_most_points_products);

        initLayout();
        setupSlider();


    }

    private void initLayout() {
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
}
