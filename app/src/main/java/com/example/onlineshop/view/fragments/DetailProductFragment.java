package com.example.onlineshop.view.fragments;


import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentDetailProductBinding;
import com.example.onlineshop.model.Attributes;
import com.example.onlineshop.view.activities.ReviewActivity;
import com.example.onlineshop.view.activities.ShoppingCartActivity;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.DetailProViewModel;

import java.util.HashMap;

public class DetailProductFragment extends VisibleFragment {
    public static final String ARG_PRODUCT = "Arg product";
    private DetailProViewModel mDetailProViewModel;
    private int mProductId;
    private Product mProduct;
    private FragmentDetailProductBinding mBinding;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    public static DetailProductFragment newInstance(int productId) {
        DetailProductFragment fragment = new DetailProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mProductId = getArguments().getInt(ARG_PRODUCT);
        }

        mDetailProViewModel = new ViewModelProvider(this).get(DetailProViewModel.class);

        mDetailProViewModel.getProductLiveData(mProductId).observe(this, product -> {
            mDetailProViewModel.setProduct(product);
            mProduct = product;
            initUI();
            setupSlider();

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_product, container, false);

        mBinding.activityProductDetailsAddToCartBtnLayout.setOnClickListener(view -> {
            mDetailProViewModel.addProduct(mProduct);
            getActivity().startActivity(ShoppingCartActivity.newIntent(getContext()));
        });


        return mBinding.getRoot();
    }

    private void initUI() {
        mBinding.tvProductDetailsMainTitle.setText(mProduct.getName());

        if (mProduct.getShortDescription() == null || mProduct.getShortDescription().equals("")) {
            mBinding.tvProductDetailsSecondaryTitle.setVisibility(View.GONE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                mBinding.tvProductDetailsSecondaryTitle.setText(Html.fromHtml(mProduct.getShortDescription(), Html.FROM_HTML_MODE_LEGACY));
            else {
                Spanned sp = Html.fromHtml(mProduct.getShortDescription());
                mBinding.tvProductDetailsSecondaryTitle.setText(sp);
            }
        }

        if (!mProduct.getStatus().equals("publish")) {
            mBinding.activityProductDetailsConfigLayout.setVisibility(View.GONE);
            mBinding.tvProductDetailsNotExist.setVisibility(View.VISIBLE);
        } else {
            if (mProduct.getAttributes() != null && mProduct.getAttributes().size() > 0) {

                if (mProduct.getAttributes().get(0).getName().equals("رنگ")) {
                    initUIColorText(mProduct.getAttributes().get(0));
                } else {
                    initUISizeText(mProduct.getAttributes().get(0));
                }

                if (mProduct.getAttributes().size() > 1) {
                    if (mProduct.getAttributes().get(1).getName().equals("رنگ")) {
                        initUIColorText(mProduct.getAttributes().get(1));
                    } else {
                        initUISizeText(mProduct.getAttributes().get(1));
                    }
                }

            }

            for (int i = 0; i < mProduct.getCategories().size(); i++) {
                if (mProduct.getCategories().get(i).getName().equals("خوردنی و آشامیدنی")) {
                    mBinding.tvProductDetailsSentFresh.setVisibility(View.VISIBLE);
                    mBinding.tvProductDetailsSentFrom.setVisibility(View.GONE);
                    break;
                } else {
                    mBinding.tvProductDetailsSentFresh.setVisibility(View.GONE);
                    mBinding.tvProductDetailsSentFrom.setVisibility(View.VISIBLE);
                }
            }

            if (mProduct.getRegularPrice() != null && mProduct.getRegularPrice() != "") {
                mBinding.tvProductDetailsPayablePrice.setText(mProduct.getRegularPrice() + " تومان");
                mBinding.tvProductDetailsRealPrice.setText(mProduct.getPrice() + " تومان");
                mBinding.tvProductDetailsRealPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mBinding.tvProductDetailsPayablePrice.setText(mProduct.getPrice() + " تومان");
                mBinding.tvProductDetailsRealPrice.setVisibility(View.GONE);
            }

            mBinding.progressBarProductDetailsAddToCart.hide();

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBinding.tvDescriptorDetailProduct.setText(Html.fromHtml(mProduct.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            Spanned sp = Html.fromHtml(mProduct.getDescription());
            mBinding.tvDescriptorDetailProduct.setText(sp);
        }

        mBinding.tvRateDetailProduct.setText(mProduct.getAverageRating());

        mBinding.activityProductDetailsRlCommentBtn.setOnClickListener(view -> {
            startActivity(ReviewActivity.newIntent(getContext(), mProductId,mProduct.getName()));
        });

    }

    private void setupSlider() {

        HashMap<String, String> url_maps = new HashMap<>();

//        for (ImagesItem imagesItem : mProduct.getImages()) {
//            url_maps.put("", imagesItem.getSrc());
//        }

        for (int i = 0; i < mProduct.getImages().size(); i++) {
            url_maps.put(mProduct.getImages().get(i).getName(), mProduct.getImages().get(i).getSrc());
        }

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

            mBinding.sliderDetailProduct.addSlider(textSliderView);
        }
        mBinding.sliderDetailProduct.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mBinding.sliderDetailProduct.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinding.sliderDetailProduct.setDuration(5000);
    }

    public void initUIColorText(Attributes attributes) {
        mBinding.tvProductDetailsColor.setVisibility(View.VISIBLE);
        mBinding.tvProductDetailsColorCount.setVisibility(View.VISIBLE);
        mBinding.tvProductDetailsColorShow.setVisibility(View.VISIBLE);
        int countColor = attributes.getOptions().size();
        mBinding.tvProductDetailsColorCount.setText(countColor + " رنگ");
        String namesColor = attributes.getOptions().get(0);
        for (int i = 1; i < countColor; i++) {
            namesColor += (" , " + attributes.getOptions().get(i));
        }
        mBinding.tvProductDetailsColorShow.setText(namesColor);
    }

    public void initUISizeText(Attributes attributes) {
        mBinding.tvProductDetailsSizeCount.setVisibility(View.VISIBLE);
        mBinding.tvProductDetailsSize.setVisibility(View.VISIBLE);
        mBinding.tvProductDetailsSizeShow.setVisibility(View.VISIBLE);
        int countSize = attributes.getOptions().size();
        mBinding.tvProductDetailsSizeCount.setText(countSize + " سایز");
        String namesSize = attributes.getOptions().get(0);
        for (int i = 1; i < countSize; i++) {
            namesSize += (" , " + attributes.getOptions().get(i));
        }
        mBinding.tvProductDetailsSizeShow.setText(namesSize);
    }


}
