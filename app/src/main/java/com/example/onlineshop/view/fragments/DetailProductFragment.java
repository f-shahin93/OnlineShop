package com.example.onlineshop.view.fragments;


import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentDetailProductBinding;
import com.example.onlineshop.view.activities.ShoppingCartActivity;
import com.example.onlineshop.model.ImagesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.viewmodel.DetailProViewModel;
import com.example.onlineshop.viewmodel.HomePageViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

public class DetailProductFragment extends Fragment {
    public static final String ARG_PRODUCT = "Arg product";
    //    private SliderLayout mSlider;
//    private TextView mTvStatusProduct,mTvPriceSale,mTvPriceRegular , mTvDescription ,mTvRate;
//    private MaterialButton mButtonAddShoppingCart;
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

        mDetailProViewModel = ViewModelProviders.of(this).get(DetailProViewModel.class);

        mDetailProViewModel.getProductLiveData(mProductId).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mDetailProViewModel.setProduct(product);
                mProduct = product;
                initUI();
                setupSlider();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_product, container, false);

        /*buttonAddToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mProductRepository.addPruductToList(mProductId);
                getActivity().startActivity(ShoppingCartActivity.newIntent(getContext()));
            }
        });*/



        return mBinding.getRoot();
    }

    private void initUI() {
//        mSlider = view.findViewById(R.id.slider_detail_product);
//        mTvStatusProduct = view.findViewById(R.id.tv_status_detail_product);
//        mTvPriceSale = view.findViewById(R.id.tv_price_sale_detail_product);
//        mTvPriceRegular = view.findViewById(R.id.tv_price_regular_detail_product);
//        mTvDescription = view.findViewById(R.id.tv_descriptor_detail_product);
//        mTvRate = view.findViewById(R.id.tv_rate_product);
//        mButtonAddShoppingCart = view.findViewById(R.id.button_add_to_shopping_cart);

        mBinding.tvProductDetailsMainTitle.setText(mProduct.getName());

        if (mProduct.getShortDescription() == null || mProduct.getShortDescription().equals("")) {
            mBinding.tvProductDetailsSecondaryTitle.setVisibility(View.GONE);
        } else
            mBinding.tvProductDetailsSecondaryTitle.setText(mProduct.getShortDescription());

        if (!mProduct.getStatus().equals("publish")) {
            mBinding.activityProductDetailsConfigLayout.setVisibility(View.GONE);
            mBinding.tvProductDetailsNotExist.setVisibility(View.VISIBLE);
        } else {
            if (mProduct.getAttributes() != null && mProduct.getAttributes().size() > 0) {
                if (mProduct.getAttributes().get(0) != null && mProduct.getAttributes().get(0).getName().equals("رنگ")) {
                    mBinding.tvProductDetailsColor.setText("رنگ");
                    int countColor = mProduct.getAttributes().get(0).getOptions().size();
                    mBinding.tvProductDetailsColorCount.setText(" رنگ" + countColor);
                    String namesColor = mProduct.getAttributes().get(0).getOptions().get(0);
                    for (int i = 1; i < countColor; i++) {
                        namesColor.concat("," + mProduct.getAttributes().get(0).getOptions().get(i));
                    }
                    mBinding.tvProductDetailsColorShow.setText(namesColor);
                }
                if (mProduct.getAttributes().size() > 1 && mProduct.getAttributes().get(1) != null && mProduct.getAttributes().get(1).getName().equals("سایز")) {
                    mBinding.tvProductDetailsSize.setText("سایز");
                    int countsize = mProduct.getAttributes().get(1).getOptions().size();
                    mBinding.tvProductDetailsSizeCount.setText(" سایز" + countsize);
                    String namesSize = mProduct.getAttributes().get(1).getOptions().get(0);
                    for (int i = 1; i < countsize; i++) {
                        namesSize.concat("," + mProduct.getAttributes().get(1).getOptions().get(i));
                    }
                    mBinding.tvProductDetailsSizeShow.setText(namesSize);
                }
            } else {
                mBinding.tvProductDetailsSizeCount.setVisibility(View.GONE);
                mBinding.tvProductDetailsSize.setVisibility(View.GONE);
                mBinding.tvProductDetailsSizeShow.setVisibility(View.GONE);
                mBinding.tvProductDetailsColorCount.setVisibility(View.GONE);
                mBinding.tvProductDetailsColor.setVisibility(View.GONE);
                mBinding.tvProductDetailsColorShow.setVisibility(View.GONE);
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
                mBinding.tvProductDetailsPayablePrice.setText(" تومان" + mProduct.getRegularPrice());
                mBinding.tvProductDetailsRealPrice.setText(" تومان" + mProduct.getPrice());
                mBinding.tvProductDetailsRealPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mBinding.tvProductDetailsPayablePrice.setText(" تومان" + mProduct.getPrice());
                mBinding.tvProductDetailsRealPrice.setVisibility(View.GONE);
            }

            mBinding.progressBarProductDetailsAddToCart.hide();
            mDetailProViewModel.getIsClickAddtoCartLiveData().observe(DetailProductFragment.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean) {
                        mDetailProViewModel.storeProduct(mProduct);
                        getActivity().startActivity(ShoppingCartActivity.newIntent(getContext()));
                    }
                }
            });

        }

        mBinding.tvDescriptorDetailProduct.setText(mProduct.getDescription());

        mBinding.tvRateDetailProduct.setText(mProduct.getAverageRating());

    }

    private void setupSlider() {

        HashMap<String, String> url_maps = new HashMap<>();

//        for (ImagesItem imagesItem : mProduct.getImages()) {
//            url_maps.put("", imagesItem.getSrc());
//        }

        for (int i = 0; i < mProduct.getImages().size(); i++) {
            url_maps.put("", mProduct.getImages().get(i).getSrc());
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

}
