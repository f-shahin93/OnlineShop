package com.example.onlineshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.controller.activities.CategoryViewPagerActivity;
import com.example.onlineshop.model.CategoriesItem;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List mList;
    private String mTagListName;

    public CategoryAdapter(Context context, List list, String tag) {
        mList = list;
        mContext = context;
        mTagListName = tag;

    }

    public void setListCategoryAdapter(List list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       // if (mTagListName.equals(MainActivity.HOME_PAGE_VIEW_PAGER_CATEGORY)) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_category_home_page, parent, false);
            return new CategoryViewHolderHp(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       // if (mTagListName.equals(MainActivity.HOME_PAGE_VIEW_PAGER_CATEGORY)) {
            ((CategoryViewHolderHp) holder).bindCategory(mList.get(position));


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class CategoryViewHolderHp extends RecyclerView.ViewHolder {

        private TextView mTvCategoryItem;

        public CategoryViewHolderHp(@NonNull View itemView) {
            super(itemView);
            mTvCategoryItem = itemView.findViewById(R.id.tv_category_home_page);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = CategoryViewPagerActivity.newIntent(mContext);
                    mContext.startActivity(intent);
                }
            });
        }

        public void bindCategory(Object object) {
            CategoriesItem categoriesItem = (CategoriesItem) object;
            mTvCategoryItem.setText(categoriesItem.getName());
        }
    }

    //delete
   /* public class CategoryViewHolderFragment extends RecyclerView.ViewHolder {

        private AppCompatImageView mImageView;
        private TextView mTvNameCategory;
        private CategoriesItem mCategoriesItem;

        public CategoryViewHolderFragment(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.Iv_item_list_category_view_pager);
            mTvNameCategory = itemView.findViewById(R.id.tv_name_item_list_category_view_pager);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = DetailListCategoryActivity.newIntent(mContext
                            ,String.valueOf(mCategoriesItem.getId())
                            ,mCategoriesItem.getName());

                    mContext.startActivity(intent);

                }
            });

        }

        public void bindCategoryFragment(CategoriesItem categoriesItem) {
            mCategoriesItem = categoriesItem;
            mTvNameCategory.setText(categoriesItem.getName());
        }
    }*/
}
