package com.example.onlineshop.network;

import android.util.Log;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.network.interfaces.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFetcher {

    private static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";

    private static final String TAG_PRODUCT = "TagProduct";

    private Map<String, String> mQueries;
    private static Retrofit mRetrofit;
    private ProductService mProductService;
    private ProductFetcherCallbacks mCallbacks;
    private List<Product> mProductList = new ArrayList<>();

    public ProductFetcher(ProductFetcherCallbacks callbacks) {

        mCallbacks = callbacks;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mQueries = new HashMap<String,String>() {{
            put("consumer_key", "ck_0c3f701d165bef89c42a0a5ef6c8388df5179c31");
            put("consumer_secret", "cs_a0dbb7a91a42aec74c91d1ff7612f7559475bd28");
        }};

        mProductService = mRetrofit.create(ProductService.class);

    }



    public void getLastProduct() {

        Call<List<Product>> call = mProductService.getProductBody(mQueries);
        call.enqueue(getRetrofitCallback());
    }


    private Callback<List<Product>> getRetrofitCallback() {
        return new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful ");

                    List<Product> list = response.body();
                    mProductList = list;
                    mCallbacks.onProductResponse(mProductList);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Log.d(TAG_PRODUCT, "Failed " + t.getMessage());
            }
        };

    }

    public interface ProductFetcherCallbacks {
        void onProductResponse(List<Product> productList);
        //void onProductResponse(List<Product> productList);
        //void onCategoryResponse(List<Category> categoryList);

    }


}

/*
 public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(USER_NAME, PASSWORD))
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return mRetrofit;
    }

    private static class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }
 */