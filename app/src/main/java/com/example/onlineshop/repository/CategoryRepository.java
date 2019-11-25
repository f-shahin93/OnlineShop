package com.example.onlineshop.repository;

import com.example.onlineshop.model.CategoriesItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private static CategoryRepository instance;
    private List<CategoriesItem> mCategoriesList;
    private List<CategoriesItem> mSubCategoriesList;


    public CategoriesItem getCategory(long categoryId) {
        for (CategoriesItem category : mCategoriesList) {
            if (category.getId() == categoryId)
                return category;
        }
        return null;
    }

    public int getCurrentCategory(long id) {
        for (int i = 0; i < mSubCategoriesList.size(); i++) {
            if (mSubCategoriesList.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    private void generateParentList() {
        for (CategoriesItem category : mCategoriesList) {
            if (category.getId() == 0)
                mSubCategoriesList.add(category);
        }
    }

    public List<CategoriesItem> getSubCategoires(long parentId) {
        List<CategoriesItem> result = new ArrayList<>();

        for (CategoriesItem category : mCategoriesList) {
            if (category.getId() == parentId)
                result.add(category);
        }
        return result;
    }


    public List<CategoriesItem> getCategoriesList() {
        return mCategoriesList;
    }

    public void setCategoriesList(List<CategoriesItem> categoriesList) {
        mCategoriesList.clear();
        mSubCategoriesList.clear();
        mCategoriesList = categoriesList;
        generateParentList();

    }

    public List<CategoriesItem> getSubCategoriesList() {
        return mSubCategoriesList;
    }

    public void setSubCategoriesList(List<CategoriesItem> subCategoriesList) {
        mSubCategoriesList = subCategoriesList;
    }

    private CategoryRepository() {
        mCategoriesList = new ArrayList<>();
        mSubCategoriesList = new ArrayList<>();
    }

    public static CategoryRepository getInstance(){
        if(instance == null){
            instance = new CategoryRepository();
        }
        return instance;
    }

}
