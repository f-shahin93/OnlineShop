package com.example.onlineshop.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShoppingCart extends RealmObject {

    @PrimaryKey
    private String id;
    private int idProduct;
    private int countProduct;


    public ShoppingCart() {
    }

    public String getId() {
        return id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }
}
