package com.example.onlineshop.network.interfaces;

import com.example.onlineshop.model.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ProductService {

    /*@GET(".")
        Call<Category> getProductCategory(@QueryMap Map<String, String> categoryQueries);*/

    @GET("products/?")
    Call<List<Product>> getProductBody(@QueryMap Map<String, String> productQueries);

    @GET("products/?")
    Call<List<Product>> getAllProducts(@Query("orderby") String orderType);


    @GET("products/{id}/?")
    Call<Product> getProduct(@Path("id") String productId);

}
