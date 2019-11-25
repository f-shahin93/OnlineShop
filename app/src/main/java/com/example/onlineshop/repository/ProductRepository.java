package com.example.onlineshop.repository;

import com.example.onlineshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static ProductRepository instance;

    private ProductRepository() {
    }

    public ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    public List<Product> getListCategory(){

        List<Product> list = new ArrayList<>();



        return list;
    }



}
