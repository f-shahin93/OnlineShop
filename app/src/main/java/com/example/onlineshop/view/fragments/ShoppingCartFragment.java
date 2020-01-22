package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.view.Adapter.ProductShoppingCartAdapter;
import com.example.onlineshop.viewmodel.DetailProViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends VisibleFragment implements ProductShoppingCartAdapter.ShoppingAdapterCallback {

    private RecyclerView mRecyclerView;
    private ProductShoppingCartAdapter mAdapter;
    private List<Product> mProductList = new ArrayList<>();
    // private ProductRepository mProductRepository = ProductRepository.getInstance();
    private TextView mTvTotalPurchasePrice;
    private DetailProViewModel mDetailProViewModel;
    private TextView mTvConfirmShopping;


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
        mDetailProViewModel = ViewModelProviders.of(this).get(DetailProViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        // mProductList = mProductRepository.getProductList();
        init(view);

        mProductList = mDetailProViewModel.getListShoppingCart();
        mTvTotalPurchasePrice.setText(String.valueOf(mDetailProViewModel.calculateTotalPriceShoppingCart()));

        //setTotalPrice();
        setupAdapter();

        mTvConfirmShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"خرید نهایی شد!",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

//    private void setTotalPrice() {
//        int numberTotal = 0;
//        for (Product product : mProductList) {
//            numberTotal += Integer.parseInt(product.getPrice());
//        }
//        mTvTotalPurchasePrice.setText(String.valueOf(numberTotal));
//    }

    private void init(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_shopping_cart);
        mTvConfirmShopping = view.findViewById(R.id.tv_confirm_shopping);
        mTvTotalPurchasePrice = view.findViewById(R.id.tv_total_purchase_price);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void setupAdapter() {
        mAdapter = new ProductShoppingCartAdapter(getContext(), mProductList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void notifyChangedList(boolean delete ,Product product) {
        if (delete) {
            // mProductList = mProductRepository.getProductList();
            //setupAdapter();
            mDetailProViewModel.deleteProduct(product);
            mProductList = mDetailProViewModel.getListShoppingCart();
            mAdapter.setListadapter(mProductList);
            mAdapter.notifyDataSetChanged();
        }
    }

}
