package com.example.onlineshop.view.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductShoppingCartAdapter extends RecyclerView.Adapter<ProductShoppingCartAdapter.ShoppingCartViewHolder> {

    private Context mContext;
    private List<Product> mProductList;
    private ShoppingAdapterCallback mCallback;

    public ProductShoppingCartAdapter(Context context, List<Product> list ,ShoppingAdapterCallback callback) {
        mContext = context;
        mProductList = list;
        mCallback = callback;
    }

    public void setListadapter(List<Product> list) {
        mProductList = list;
    }


    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_shopping_cart, parent, false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {
        holder.bind(mProductList.get(position));

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDescription, mTvTitle, mTvPriceFinal, mTvTotalPrice, mTvDelete;
        private AppCompatSpinner mSpinner;
        private AppCompatImageView mIvProduct;
        private Product mProductVh;
        private List countProductSpinner;
        private ArrayAdapter mArrayAdapter;
        private int tempSpItem;

        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.title_product_shopping_cart);
            mTvDescription = itemView.findViewById(R.id.description_product_shopping_cart);
            mTvPriceFinal = itemView.findViewById(R.id.final_price_shopping_cart);
            mTvTotalPrice = itemView.findViewById(R.id.total_price_shopping_cart);
            mIvProduct = itemView.findViewById(R.id.Iv_product_shopping_cart);
            mSpinner = itemView.findViewById(R.id.spinner_shopping_cart);
            mTvDelete = itemView.findViewById(R.id.tv_delete_product_from_shopping_cart);

            countProductSpinner = new ArrayList();
            for (int i = 0; i < 10; i++) {
                countProductSpinner.add(i);
            }
            mArrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, countProductSpinner);
            mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(mArrayAdapter);
            mSpinner.setSelection(0);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    tempSpItem = (int) adapterView.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            mTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("اطمینان دارید که این محصول حذف شود؟")
                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ProductRepository.getInstance().deletePruductFromList(mProductVh);
                                    mCallback.notifyChangedList(true);
                                }
                            })
                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create();
                }
            });


        }

        public void bind(Product product) {
            mProductVh = product;
            mTvTitle.setText(mProductVh.getName());
            mTvDescription.setText(mProductVh.getShortDescription());

            if (mProductVh.getRegularPrice() == null) {
                mTvTotalPrice.setText(mProductVh.getPrice());
            } else {
                mTvTotalPrice.setText(mProductVh.getRegularPrice());
            }

            if (mProductVh.getSalePrice() == null) {
                mTvPriceFinal.setText(mProductVh.getPrice());
            } else {
                mTvPriceFinal.setText(mProductVh.getSalePrice());
            }

            Picasso.with(mContext)
                    .load(mProductVh.getImages().get(0).getSrc())
                    .resize(100, 100)
                    .placeholder(R.drawable.place_holder_shopping_cart)
                    .into(mIvProduct);
        }

    }

    public interface ShoppingAdapterCallback {
        void notifyChangedList(boolean flagDeleteItem);
    }

}
