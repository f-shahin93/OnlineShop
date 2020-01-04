package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.view.Adapter.CategoryAdapter;
import com.example.onlineshop.viewmodel.ViewPagerCategViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SubCategoryFragment extends Fragment {

    public static final String ARG_ID_CATEGORY = "Arg idCategory";
    public static final String SUB_CATEGORY_FRAGMENT = "SubCategoryFragment";
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private String mDisplaySubCategory = "subcategories";
    private int mIdParentCategory;
    private List<Categories> mCategoriesList = new ArrayList<>();
    private ViewPagerCategViewModel mCategViewModel;


    public static SubCategoryFragment newInstance(int idParentCategory) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_CATEGORY, idParentCategory);
        fragment.setArguments(args);
        return fragment;
    }

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdParentCategory = getArguments().getInt(ARG_ID_CATEGORY);
        }

        mCategViewModel = ViewModelProviders.of(this).get(ViewPagerCategViewModel.class);

        mCategViewModel.getSubCategoriesMutableLiveData(mDisplaySubCategory, mIdParentCategory)
                .observe(this, categories -> {
                    mCategoriesList = categories;
                    setupAdapter();
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sub_category, container, false);

        init(root);

        //setupAdapter();

        return root;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mAdapter = new CategoryAdapter(getContext(), mCategoriesList, SUB_CATEGORY_FRAGMENT);
            mRecyclerView.setAdapter(mAdapter);
        }
//        if (isAdded()) {
//            if (mAdapter == null) {
//                mAdapter = new CategoryAdapter(getContext(), mCategoriesList, SUB_CATEGORY_FRAGMENT);
//                mRecyclerView.setAdapter(mAdapter);
//            } else {
//                mAdapter.setListCategoryAdapter(mCategoriesList);
//                mAdapter.notifyDataSetChanged();
//            }
//        }
    }

    private void init(View root) {
        mRecyclerView = root.findViewById(R.id.recycler_view_sub_categories_frag_viewp);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
