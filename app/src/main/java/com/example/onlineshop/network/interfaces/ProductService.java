package com.example.onlineshop.network.interfaces;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.model.customers.Billing;
import com.example.onlineshop.model.customers.Customers;
import com.example.onlineshop.model.customers.Shipping;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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
            , @Query("display") String displayCategories, @Query("parent") String parentCategories);

    @GET("products/categories")
    Call<List<Categories>> getAllCategories(@QueryMap Map<String, String> productQueries);

    @GET("products/?")
    Call<List<Product>> getProductsSubCategoires(@QueryMap Map<String, String> productQueries
            , @Query("category") String categoryId, @Query("name") String name);

    @FormUrlEncoded
    @POST("customers")
    Call<Customers> setCustomers(@QueryMap Map<String, String> productQueries,
                                 @Field("email") String email,
                                 @Field("first_name") String first_name,
                                 @Field("last_name") String last_name,
                                 @Field("username") String username);

//    @FormUrlEncoded
//    @POST("customers")
//    Call<Customers> setCustomers(@Header("Content-Type") String userkey,
//                                 @QueryMap Map<String, String> productQueries,
//                                 @Field("email") String email,
//                                 @Field("first_name") String first_name,
//                                 @Field("last_name") String last_name,
//                                 @Field("username") String username,
//                                 @Field("billing") Billing billing,
//                                 @Field("shipping") Shipping shipping);


}
