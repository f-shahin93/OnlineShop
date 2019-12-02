package com.example.onlineshop.repository;

import com.example.onlineshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static ProductRepository instance;
    private List<Product> mProductList;

    private ProductRepository() {
        mProductList = new ArrayList<>();
    }


    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    public List<Product> getListCategory(){

        List<Product> list = new ArrayList<>();

        return list;
    }

    public void addPruductToList(Product product){
        mProductList.add(product);
    }

    public void deletePruductFromList(Product product){
        mProductList.remove(product);
    }



}
