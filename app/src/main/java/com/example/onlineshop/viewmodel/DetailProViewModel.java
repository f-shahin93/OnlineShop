package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.repository.ProductRepository;

import java.util.List;

public class DetailProViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;
    private MutableLiveData<Product> mProductLiveData;
    private MutableLiveData<Boolean> mIsClickAddtoCartLiveData = new MutableLiveData<>();
    private ItemShopFetcher mShopFetcher;
    private Product mProduct;
    private List<Product> mProductListShoppingCart;


    public DetailProViewModel(@NonNull Application application) {
        super(application);
        mProductRepository = ProductRepository.getInstance();
        mShopFetcher = ItemShopFetcher.getInstance();

    }

    public MutableLiveData<Product> getProductLiveData(int productId) {
        mShopFetcher.getProductById(String.valueOf(productId));
        mProductLiveData = mShopFetcher.getProductLiveData();
        return mProductLiveData;
    }

    public void setProductLiveData(MutableLiveData<Product> productLiveData) {
        mProductLiveData = productLiveData;
    }

    public void storeProduct(Product product) {
        mProductRepository.addPruductToList(product);
    }

    public void deleteProduct(Product product) {
        mProductRepository.deletePruductFromList(product);
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }


    public MutableLiveData<Boolean> getIsClickAddtoCartLiveData() {
        return mIsClickAddtoCartLiveData;
    }

    public void setIsClickAddtoCartLiveData() {
        mIsClickAddtoCartLiveData.setValue(true);
    }

    public int calculateTotalPriceShoppingCart() {
        int numberTotal = 0;
        for (Product product : mProductListShoppingCart) {
            if (product.getRegularPrice().equals("")) {
                numberTotal += Integer.parseInt(product.getPrice());
            }else
                numberTotal += Integer.parseInt(product.getRegularPrice());
        }
        return numberTotal;
    }

    public List<Product> getListShoppingCart() {
        mProductListShoppingCart = mProductRepository.getProductList();
        return mProductListShoppingCart;
    }


}
