package com.example.onlineshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> mList;

    public HomePageAdapter(Context context, List<Product> list) {
        mList = list;
        mContext = context;
    }

    public void setListadapter(List<Product> list) {
        this.mList = list;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_card_view, parent, false);
        return new ProductViewHolder(view);
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

        private AppCompatImageView mIvproduct;
        private TextView mTvProductName;
        private TextView mTvProductPrice;
        private Product mProductVh;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            mIvproduct = itemView.findViewById(R.id.Iv_product);
            mTvProductName = itemView.findViewById(R.id.tv_name_product);
            mTvProductPrice = itemView.findViewById(R.id.tv_price_product);
        }

        public void bind(Product product) {

            mProductVh = product;

            //mIvproduct.setBackground( mProductVh.getImages().get(0));
            mTvProductName.setText(mProductVh.getName());
            mTvProductPrice.setText(mProductVh.getPrice());
        }
    }
}
