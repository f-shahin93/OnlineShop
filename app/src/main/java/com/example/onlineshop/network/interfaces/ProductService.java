package com.example.onlineshop.network.interfaces;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ProductService {

    @GET(".")
        Call<CategoriesItem> getProductCategory(@QueryMap Map<String, String> categoryQueries);

    @GET("products/?")
    Call<List<Product>> getProductBody(@QueryMap Map<String, String> productQueries);

    @GET("products/?")
    Call<List<Product>> getAllProducts(@Query("orderby") String orderType);

    @GET("products/?per_page=100")
    Call<List<Product>> getAllProductsPage();

    @GET("products/{id}/?")
    Call<Product> getProduct(@Path("id") String productId);

    @GET("products/categories")
    Call<List<CategoriesItem>> getAllCategories();

    @GET("products/categories/?per_page=100")
    Call<List<CategoriesItem>> getSubCategories(@Query("parent") String categoryId);

    @GET("products/?")
    Call<List<Product>> getProductsSubCategoires(@Query("page") String pageNumber, @Query("category") String categoryId
            , @Query("orderby") String orderBy, @Query("order") String order, @Query("attribute_term") String... attributes);

    @GET("products/?")
    Call<List<Product>> getProductsSubCategoires(@Query("category") String categoryId
            , @Query("name") String name);


}
