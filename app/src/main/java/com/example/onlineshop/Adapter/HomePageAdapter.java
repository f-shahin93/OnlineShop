package com.example.onlineshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.controller.DetailListCategoryFragment;
import com.example.onlineshop.controller.DetailProductActivity;
import com.example.onlineshop.controller.SearchProductListFragment;
import com.example.onlineshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> mList;
    private String mTag;

    public HomePageAdapter(Context context, List<Product> list, String tag) {
        mList = list;
        mContext = context;
        mTag = tag;
    }

    public void setListadapter(List<Product> list) {
        this.mList = list;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (mTag.equals(DetailListCategoryFragment.Detail_List_Category_Fragment)
                || mTag.equals(SearchProductListFragment.Search_Product_List_Fragment)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_product_catogory, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_card_view, parent, false);
        }
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
        private TextView mTvProductName, mTvRegularPrice, mTvProductPrice;
        private Product mProductVh;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            //itemView.findViewById(R.id.tv_name_item_list_category);
            // itemView.findViewById(R.id.tv_price_sale_item_list_category);
            mTvRegularPrice = itemView.findViewById(R.id.tv_price_regular_item_list_category);
            mIvproduct = itemView.findViewById(R.id.Iv_product);
            mTvProductName = itemView.findViewById(R.id.tv_name_product);
            mTvProductPrice = itemView.findViewById(R.id.tv_price_product);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = DetailProductActivity.newIntent(mContext, mProductVh);
                    mContext.startActivity(intent);
                }
            });
        }


        public void bind(Product product) {
            mProductVh = product;

            Picasso.with(mContext).load(mProductVh.getImages().get(0).getSrc()).into(mIvproduct);
            mTvProductName.setText(mProductVh.getName());
            mTvProductPrice.setText(mProductVh.getPrice());

            if (mTvRegularPrice != null && mTag.equals(DetailListCategoryFragment.Detail_List_Category_Fragment)) {
                mTvRegularPrice.setText(mProductVh.getRegularPrice());
                mTvProductPrice.setText(mProductVh.getSalePrice());
            }
        }
    }

}
