package com.example.onlineshop.view.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemListProductCatogoryBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.activities.DetailProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListSubCategoryAdapter extends RecyclerView.Adapter<ProductListSubCategoryAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> mList;

    public ProductListSubCategoryAdapter(Context context, List<Product> list) {
        mList = list;
        mContext = context;
    }

    public void setListadapter(List<Product> list) {
        this.mList = list;
    }


    @NonNull
    @Override
    public ProductListSubCategoryAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListProductCatogoryBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext), R.layout.item_list_product_catogory, parent, false);
        return new ProductViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private Product mProductVh;
        private ItemListProductCatogoryBinding mBinding;

        public ProductViewHolder(ItemListProductCatogoryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.getRoot().setOnClickListener(view -> {
                Intent intent = DetailProductActivity.newIntent(mContext, mProductVh.getId());
                mContext.startActivity(intent);
            });
        }


        public void bind(Product product) {
            mProductVh = product;

            if (mProductVh.getImages().size() > 0)
                Picasso.with(mContext)
                        .load(mProductVh.getImages().get(0).getSrc())
                        .placeholder(R.drawable.place_holder_shopping_cart)
                        .into(mBinding.IvProductCategoryViewPager);

            mBinding.tvNameProductCategoryViewPager.setText(mProductVh.getName());

            if(mProductVh.getRegularPrice().equals("")){
                mBinding.tvPriceRegularItemListCategoryViewPager.setVisibility(View.INVISIBLE);
                mBinding.tvPriceProductCategoryViewPager.setText(mProductVh.getPrice()+" تومان");
            }else {
                mBinding.tvPriceRegularItemListCategoryViewPager.setVisibility(View.VISIBLE);
                mBinding.tvPriceProductCategoryViewPager.setText(mProductVh.getPrice()+" تومان");
                mBinding.tvPriceRegularItemListCategoryViewPager.setText(mProductVh.getRegularPrice()+" تومان");
                mBinding.tvPriceRegularItemListCategoryViewPager.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

        }
    }
}
