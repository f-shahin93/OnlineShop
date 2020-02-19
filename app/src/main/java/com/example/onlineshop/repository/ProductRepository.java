package com.example.onlineshop.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.ShoppingCart;
import com.example.onlineshop.network.ItemShopFetcher;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ProductRepository {

    private static ProductRepository instance;
    private MutableLiveData<List<Product>> mProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<Double> mTotalPriceOfCartProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mTotalCountProductsCartLiveData = new MutableLiveData<>();
    private Realm mRealm;
    private ItemShopFetcher mShopFetcher;
    private Context mContext;


    private ProductRepository(Context context) {
        mRealm = Realm.getDefaultInstance();
        mShopFetcher = ItemShopFetcher.getInstance();
        mContext = context;
    }

    public static ProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductRepository(context);
        }
        return instance;
    }

    public void setProductsListShoppingCart() {
        List<Integer> productIdList = new ArrayList<>();

        for (ShoppingCart shoppingCart : mRealm.where(ShoppingCart.class).findAll()) {
            productIdList.add(shoppingCart.getIdProduct());
        }

        new GetProductListAsyncTask().execute(productIdList);

    }

    public void addPruductToList(Product product) {
        mRealm.beginTransaction();
        if (!addCountProductFromCart(product)) {
            ShoppingCart shoppingCart = mRealm.createObject(ShoppingCart.class, String.valueOf(product.getId()));
            shoppingCart.setIdProduct(product.getId());
            shoppingCart.setCountProduct(1);
        }

        if (mRealm.isInTransaction())
            mRealm.commitTransaction();

        mTotalCountProductsCartLiveData.setValue(mRealm.where(ShoppingCart.class).findAll().size());
        mTotalPriceOfCartProductsLiveData.setValue(calculateTotalPriceProductsCart());
    }

    private boolean addCountProductFromCart(Product product) {
        if (!mRealm.isInTransaction())
            mRealm.beginTransaction();

        for (ShoppingCart shoppingCart : mRealm.where(ShoppingCart.class).findAll()) {
            if (shoppingCart.getIdProduct() == product.getId()) {
                if (shoppingCart.getCountProduct() >= 10)
                    return true;

                shoppingCart.setCountProduct(shoppingCart.getCountProduct() + 1);

                List<Product> changeProductList = mProductListLiveData.getValue();
                for (Product changedproduct : changeProductList) {
                    if (changedproduct.getId() == product.getId()) {
                        if (changedproduct.getCountProductInCart().getValue() != null)
                            changedproduct.getCountProductInCart()
                                    .setValue(changedproduct.getCountProductInCart().getValue() + 1);
                        else
                            changedproduct.getCountProductInCart().setValue(1);
                    }
                }
                mProductListLiveData.setValue(changeProductList);
                mRealm.commitTransaction();
                return true;
            }
        }
        return false;
    }

    private Double calculateTotalPriceProductsCart() {
        double numberTotal = 0;
        if (mProductListLiveData.getValue() != null)
            for (Product product : mProductListLiveData.getValue()) {
                if (product.getCountProductInCart().getValue() != null) {
                    if (product.getRegularPrice().equals(""))
                        numberTotal += product.getCountProductInCart().getValue() *
                                (Double.parseDouble(product.getPrice()));
                    else
                        numberTotal += product.getCountProductInCart().getValue() *
                                (Double.parseDouble(product.getRegularPrice()));
                }
            }
        return numberTotal;

    }

    public void deletePruductFromListCart(Product product) {
        if (!mRealm.isInTransaction())
            mRealm.beginTransaction();

        for (ShoppingCart shoppingCart : mRealm.where(ShoppingCart.class).findAll()) {
            if (shoppingCart.getIdProduct() == product.getId()) {
                shoppingCart.deleteFromRealm();

                List<Product> changeProductList = mProductListLiveData.getValue();
                for (Product changeProduct : changeProductList) {
                    if (changeProduct.getId() == product.getId()) {
                        changeProductList.remove(changeProduct);
                        break;
                    }
                }
                mProductListLiveData.setValue(changeProductList);
                break;
            }
        }
        mTotalPriceOfCartProductsLiveData.setValue(calculateTotalPriceProductsCart());
        mTotalCountProductsCartLiveData.setValue(mRealm.where(ShoppingCart.class).findAll().size());
        mRealm.commitTransaction();

    }

    public void changingNumOfProductFromCart(Product product, int countOfProduct) {
        if (!mRealm.isInTransaction())
            mRealm.beginTransaction();

        for (ShoppingCart shoppingCart : mRealm.where(ShoppingCart.class).findAll()) {
            if (shoppingCart.getIdProduct() == product.getId()) {
                shoppingCart.setCountProduct(countOfProduct);

                List<Product> changeProductList = mProductListLiveData.getValue();
                for (Product changeProduct : changeProductList) {
                    if (changeProduct.getId() == product.getId()) {
                        changeProduct.getCountProductInCart().setValue(countOfProduct);
                        break;
                    }
                }
                mProductListLiveData.setValue(changeProductList);
                break;
            }
        }

        mRealm.commitTransaction();
        mTotalPriceOfCartProductsLiveData.setValue(calculateTotalPriceProductsCart());
    }

    public void getCountCartFromDB() {
        mTotalCountProductsCartLiveData.setValue(mRealm.where(ShoppingCart.class).findAll().size());
    }


    public MutableLiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public MutableLiveData<Double> getTotalPriceOfCartProductsLiveData() {
        return mTotalPriceOfCartProductsLiveData;
    }

    public MutableLiveData<Integer> getTotalCountProductsCartLiveData() {
        return mTotalCountProductsCartLiveData;
    }

    class GetProductListAsyncTask extends AsyncTask<List<Integer>, Void, Void> {

        @Override
        protected Void doInBackground(List<Integer>... lists) {
            mShopFetcher.getProducstListByIdProduct(lists[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("TagProduct", "on post execute");
            List<Product> productList = new ArrayList<>();

            for (Product product : mShopFetcher.getProductListShoppingCart()) {
                for (ShoppingCart shoppingCart : mRealm.where(ShoppingCart.class).findAll()) {
                    if (shoppingCart.getIdProduct() == product.getId()) {
                        product.getCountProductInCart().postValue(shoppingCart.getCountProduct());
                        productList.add(product);
                        break;
                    }
                }
            }

            mProductListLiveData.postValue(productList);
            mTotalCountProductsCartLiveData.postValue(mRealm.where(ShoppingCart.class).findAll().size());
            mTotalPriceOfCartProductsLiveData.postValue(calculateTotalPriceProductsCart());
        }
    }

}
