package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.User;
import com.example.onlineshop.model.customers.Billing;
import com.example.onlineshop.model.customers.Shipping;
import com.example.onlineshop.model.orders.LineItemsItem;
import com.example.onlineshop.model.orders.Orders;
import com.example.onlineshop.model.review.Review;
import com.example.onlineshop.network.ItemShopFetcher;
import com.example.onlineshop.prefs.QueryPreferences;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class DetailProViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;
    private MutableLiveData<Product> mProductLiveData;
    private MutableLiveData<Boolean> mIsClickAddtoCartLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsClickDeleteItemLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsChangingItemCartLiveData = new MutableLiveData<>();
    private ItemShopFetcher mShopFetcher;
    private Product mProduct;
    private Product mDelProduct;
    private Context mContext;
    private UserRepository mUserRepository;


    public DetailProViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = UserRepository.getInstance(application);
        mProductRepository = ProductRepository.getInstance(application);
        mShopFetcher = ItemShopFetcher.getInstance();
        mContext = application;
    }

    public MutableLiveData<Product> getProductLiveData(int productId) {
        mShopFetcher.getProductById(String.valueOf(productId));
        mProductLiveData = mShopFetcher.getProductLiveData();
        return mProductLiveData;
    }

    public MutableLiveData<List<Review>> getProductReviewsListLiveData(int productId) {
        mShopFetcher.getProductReviewsList(String.valueOf(productId));
        return mShopFetcher.getProductReviewsListMLiveData();
    }

    public void addProduct(Product product) {
        mProductRepository.addPruductToList(product);
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

    public MutableLiveData<Boolean> getIsChangingItemCartLiveData() {
        return mIsChangingItemCartLiveData;
    }

    public MutableLiveData<Boolean> getIsClickDeleteItemLiveData() {
        return mIsClickDeleteItemLiveData;
    }

    public MutableLiveData<List<Product>> getListShoppingCart() {
        return mProductRepository.getProductListLiveData();
    }

    public void setProductListRepo() {
        mProductRepository.setProductsListShoppingCart();
    }

    public MutableLiveData<Double> getTotalPrice() {
        return mProductRepository.getTotalPriceOfCartProductsLiveData();
    }

    public MutableLiveData<Integer> getCountProductCart() {
        return mProductRepository.getTotalCountProductsCartLiveData();
    }

    public void initCountCart(){
        mProductRepository.getCountCartFromDB();
    }

    public MutableLiveData<Boolean> showDeleteDialog(Product product, boolean isDelete) {
        if (isDelete) {
            mIsClickDeleteItemLiveData.setValue(true);
            mProductRepository.deletePruductFromListCart(product);
        } else
            mIsClickDeleteItemLiveData.setValue(false);
        return mIsClickDeleteItemLiveData;
    }

    public void changeCountProductListCart(Product product, int countOfProduct) {
        mProductRepository.changingNumOfProductFromCart(product, countOfProduct);
        mIsChangingItemCartLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> postOrders(List<Product> productList) {
        if (QueryPreferences.getStatusLogin(mContext)) {
            User user = mUserRepository.getUser();
            List<LineItemsItem> lineItemsItems = new ArrayList<>();
            Billing billing = new Billing();
            Shipping shipping = new Shipping();

            for (Product product : productList) {
                LineItemsItem line = new LineItemsItem();
                line.setQuantity(product.getCountProductInCart().getValue());
                line.setProductId(product.getId());
                lineItemsItems.add(line);
            }

            billing.setEmail(user.getEmail());
            billing.setFirstName(user.getFirstName());
            billing.setLastName(user.getLastName());

            shipping.setFirstName(user.getFirstName());
            shipping.setLastName(user.getLastName());

            Orders order = new Orders(lineItemsItems,billing,shipping,user.getIdUser());
            return mShopFetcher.setOrders(order);

        } else{
             MutableLiveData<Boolean> isPostLD = new MutableLiveData<>();
             isPostLD.setValue(false);
             return isPostLD;
        }
    }
}
