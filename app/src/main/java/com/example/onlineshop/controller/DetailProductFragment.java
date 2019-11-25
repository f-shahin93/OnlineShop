package com.example.onlineshop.controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.onlineshop.R;
import com.example.onlineshop.model.ImagesItem;
import com.example.onlineshop.model.Product;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProductFragment extends Fragment {
    public static final String ARG_PRODUCT = "Arg product";
    private SliderLayout mSlider;
    private TextView mTvStatusProduct,mTvPriceSale,mTvPriceRegular , mTvDescription ,mTvRate;
    private MaterialButton mButtonAddShoppingCart;
    private Product mProduct;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    public static DetailProductFragment newInstance(Product product) {
        DetailProductFragment fragment = new DetailProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = getArguments().getParcelable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_detail_product, container, false);

        initUI(view);
        setupSlider();

        mButtonAddShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    private void initUI(View view) {
        mSlider = view.findViewById(R.id.slider_detail_product);
        mTvStatusProduct = view.findViewById(R.id.tv_status_detail_product);
        mTvPriceSale = view.findViewById(R.id.tv_price_sale_detail_product);
        mTvPriceRegular = view.findViewById(R.id.tv_price_regular_detail_product);
        mTvDescription = view.findViewById(R.id.tv_descriptor_detail_product);
        mTvRate = view.findViewById(R.id.tv_rate_product);
        mButtonAddShoppingCart = view.findViewById(R.id.button_add_to_shopping_cart);

        mTvDescription.setText(mProduct.getDescription());
        mTvRate.setText(mProduct.getAverageRating());
        mTvPriceRegular.setText(mProduct.getRegularPrice());
        mTvPriceSale.setText(mProduct.getSalePrice());
        mTvStatusProduct.setText(mProduct.getStatus());

    }

    private void setupSlider() {

        HashMap<String, String> url_maps = new HashMap<>();

        for(ImagesItem imagesItem : mProduct.getImages()){
            url_maps.put("",imagesItem.getSrc());
        }

        /*for (int i = 0 ;i<mProduct.getImages().size();i++){
            url_maps.put("",mProduct.getImages().get(i).getSrc());
        }*/

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

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setDuration(5000);
    }

}
