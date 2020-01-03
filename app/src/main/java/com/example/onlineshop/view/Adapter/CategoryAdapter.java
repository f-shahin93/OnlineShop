package com.example.onlineshop.view.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemListCategoryHomePageBinding;
import com.example.onlineshop.databinding.ItemListSubcategoriesBinding;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.view.activities.CategoryViewPagerActivity;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.view.activities.ProductListSubCategoriesActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Categories> mList;
    private ItemListSubcategoriesBinding mSubcategoriesBinding;
    private ItemListCategoryHomePageBinding mHomePageBinding;
    private String mTagFragment;

    public CategoryAdapter(Context context, List<Categories> list, String tagFrag) {
        mList = list;
        mContext = context;
        mTagFragment = tagFrag;
    }

    public void setListCategoryAdapter(List<Categories> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (mTagFragment.equals("SubCategoryFragment")) {
            mSubcategoriesBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(mContext), R.layout.item_list_subcategories, parent, false);
            return new SubCategoriesViewHolderFragment(mSubcategoriesBinding.getRoot());

        } else if (mTagFragment.equals("MainFragment") || mTagFragment.equals("SearchProductListFragment"))
            mHomePageBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(mContext), R.layout.item_list_category_home_page, parent, false);
            return new CategoryViewHolderHp(mHomePageBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(mTagFragment.equals("SubCategoryFragment"))
            ((SubCategoriesViewHolderFragment)holder).bindSubCategories(mList.get(position));
        else if(mTagFragment.equals("MainFragment") || mTagFragment.equals("SearchProductListFragment"))
            ((CategoryViewHolderHp)holder).bindCategory(mList.get(position));

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class CategoryViewHolderHp extends RecyclerView.ViewHolder {

        private Categories mCategories;
        private CategoryViewHolderHp(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(view -> {
                Intent intent = CategoryViewPagerActivity.newIntent(mContext);
                mContext.startActivity(intent);

            });
        }

        private void bindCategory(Categories categoriesItem) {
            mCategories = categoriesItem;
            mHomePageBinding.tvCategoryHomePage.setText(mCategories.getName());
        }
    }

    private class SubCategoriesViewHolderFragment extends RecyclerView.ViewHolder {


        private Categories mCategories;

        private SubCategoriesViewHolderFragment(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ProductListSubCategoriesActivity.newIntent(mContext
                            , mCategories.getId()
                            , mCategories.getName());

                    mContext.startActivity(intent);

                }
            });
        }

        private void bindSubCategories(Categories categories) {
            mCategories = categories;
            mSubcategoriesBinding.tvSubCategoriesFragViewp.setText(categories.getName());
            Picasso.with(mContext)
                    .load(categories.getImage().getSrc())
                    .placeholder(R.drawable.place_holder_shopping_cart)
                    .into(mSubcategoriesBinding.ivSubCategoriesFragViewp);
        }
    }
}
