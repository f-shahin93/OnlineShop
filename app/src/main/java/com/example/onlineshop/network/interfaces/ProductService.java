package com.example.onlineshop.network.interfaces;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.model.customers.Customers;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface ProductService {

    @GET(".")
        Call<CategoriesItem> getProductCategory(@QueryMap Map<String, String> categoryQueries);

    @GET("products/?")
    Call<List<Product>> getProductBody(@QueryMap Map<String, String> productQueries);

    @GET("products/?")
    Call<List<Product>> getAllProductsByPage(@QueryMap Map<String, String> productQueries);

    @GET("products/{id}/?")
    Call<Product> getProduct(@Path("id") String productId, @QueryMap Map<String, String> productQueries);

    @GET("products/categories")
    Call<List<Categories>> getAllCategories(@QueryMap Map<String, String> productQueries
            , @Query("display") String displayCategories ,@Query("parent") String parentCategories );

    @GET("products/categories/?per_page=10")
    Call<List<CategoriesItem>> getSubCategories(@Query("parent") String categoryId);

    @GET("products/?")
    Call<List<Product>> getProductsSubCategoires(@Query("category") String categoryId
            , @Query("name") String name);

    @GET("products/?")
    Call<List<Product>> getProductsSubCategoires(@QueryMap Map<String, String> productQueries
            ,@Query("category") String categoryId, @Query("name") String name);

    @POST("customers")
    Call<Customers> setCustomers(@Query("customers") Customers customers);



}
