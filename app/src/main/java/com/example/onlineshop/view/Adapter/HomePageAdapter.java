package com.example.onlineshop.view.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemListCardViewHomePBinding;
import com.example.onlineshop.view.activities.DetailProductActivity;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.HomePageViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> mList;
    private HomePageViewModel mHomeViewModel;

    public HomePageAdapter(Context context, List<Product> list) {
        mList = list;
        mContext = context;

    }

    public void setListadapter(List<Product> list) {
        this.mList = list;
    }


    @NonNull
    @Override
    public HomePageAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCardViewHomePBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext), R.layout.item_list_card_view_home_p, parent, false);
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

        private ItemListCardViewHomePBinding mBinding;
        private Product mProductVh;

        public ProductViewHolder(ItemListCardViewHomePBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = DetailProductActivity.newIntent(mContext, mProductVh.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(Product product) {
            mProductVh = product;

            if (mProductVh.getImages().size() > 0)
                Picasso.with(mContext)
                        .load(mProductVh.getImages().get(0).getSrc())
                        .placeholder(R.drawable.place_holder_shopping_cart)
                        .into(mBinding.IvProductCardVHomePage);
            mBinding.tvNameProductCardVHomePage.setText(mProductVh.getName());
            mBinding.tvPriceProductCardVHomePage.setText(mProductVh.getPrice());

        }
    }

}
