package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.Adapter.ProductShoppingCartAdapter;
import com.example.onlineshop.viewmodel.DetailProViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends VisibleFragment {

    private RecyclerView mRecyclerView;
    private ProductShoppingCartAdapter mAdapter;
    private List<Product> mProductList;
    private TextView mTvTotalPriceOfCartProducts;
    private DetailProViewModel mDetailProViewModel;
    //private TextView mTvConfirmShopping;
    private MaterialButton mTvConfirmShopping;


    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        mDetailProViewModel = new ViewModelProvider(this).get(DetailProViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        init(view);

        if (mDetailProViewModel.getTotalPrice().getValue() == null)
            mTvTotalPriceOfCartProducts.setText(0 + " تومان");
        else
            mTvTotalPriceOfCartProducts.setText(String.valueOf(mDetailProViewModel.getTotalPrice().getValue()));
        mDetailProViewModel.getTotalPrice().observe(this, aDouble ->
                mTvTotalPriceOfCartProducts.setText(aDouble + " تومان"));

        mDetailProViewModel.setProductListRepo();
        mDetailProViewModel.getListShoppingCart().observe(this, list -> {
            Log.d("TagProduct", "getListShoppingCart().observe : ");
            mProductList = list;
            setupAdapter();
        });


        mDetailProViewModel.getIsClickDeleteItemLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                mProductList = mDetailProViewModel.getListShoppingCart().getValue();
                setupAdapter();
            }
        });

        mDetailProViewModel.getIsChangingItemCartLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                mTvTotalPriceOfCartProducts.setText(String.valueOf(mDetailProViewModel.getTotalPrice()));
            }
        });

        mTvConfirmShopping.setOnClickListener(view1 ->{
            mDetailProViewModel.postOrders(mProductList).observe(this, aBoolean -> {
                if(aBoolean){
                    Toast.makeText(getContext(), "خرید نهایی شد!", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "مجددا تلاش کنید!", Toast.LENGTH_SHORT).show();
            });
                });


        return view;
    }

    private void init(View view) {
        mTvConfirmShopping = view.findViewById(R.id.tv_confirm_shopping);
        mRecyclerView = view.findViewById(R.id.recycler_view_shopping_cart);
        mTvTotalPriceOfCartProducts = view.findViewById(R.id.tv_total_price_of_cart_products);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void setupAdapter() {
        if (isAdded()) {
            mAdapter = new ProductShoppingCartAdapter(getContext(), mProductList);
            mRecyclerView.setAdapter(mAdapter);
//            if (mAdapter != null) {
//                mAdapter.setListadapter(mProductList);
//                mAdapter.notifyDataSetChanged();
//
//            } else {
//                mAdapter = new ProductShoppingCartAdapter(getContext(), mProductList);
//                mRecyclerView.setAdapter(mAdapter);
//            }
        }
    }

}
