package com.example.onlineshop.network;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.customers.Customers;
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

public class ItemShopFetcher {

    private static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";

    private static final String TAG_PRODUCT = "TagProduct";

    private Map<String, String> mQueries;
    private static Retrofit mRetrofit;
    private ProductService mProductService;
    private MutableLiveData<List<CategoriesItem>> mListCategoryLiveData;

    //private ProductFetcherCallbacks mCallbacks;

    public ItemShopFetcher() {

        //mCallbacks = callbacks;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mQueries = new HashMap<String, String>() {{
            put("consumer_key", "ck_0c3f701d165bef89c42a0a5ef6c8388df5179c31");
            put("consumer_secret", "cs_a0dbb7a91a42aec74c91d1ff7612f7559475bd28");
        }};

        mProductService = mRetrofit.create(ProductService.class);

    }

    public MutableLiveData<List<Product>> getLastProduct(String status) {

        MutableLiveData<List<Product>> mListProductLiveData = new MutableLiveData<>();
        mQueries.put("orderby", status);
        Call<List<Product>> call = mProductService.getProductBody(mQueries);
        call.enqueue(getRetrofitCallback(mListProductLiveData));
        return mListProductLiveData;
    }

    public MutableLiveData<List<Product>> getLastProduct() {
        MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
        Call<List<Product>> call = mProductService.getProductBody(mQueries);
        call.enqueue(getRetrofitCallback(mutableLiveData));
        return mutableLiveData;
    }

    public MutableLiveData<Product> getProductById(String productId) {
        MutableLiveData<Product> productLiveData = new MutableLiveData<>();
       // mQueries.remove("orderby");
        Call<Product> call = mProductService.getProduct(productId,mQueries);
        call.enqueue(getRetrofitProduct(productLiveData));
        return productLiveData;
    }

//    public Response getSpecificProduct(int id) throws IOException {
//        mQueries.remove(ORDERBY);
//        Call<Response> call = mApiInterfaces.getSpecificProduct(String.valueOf(id), mQueries);
//
//        return call.execute().body();
//    }

    public void setCustomer(Customers customer) {
        Call<Customers> customersCall = mProductService.setCustomers(customer);
        customersCall.enqueue(setCustomerwithRetrofit());
    }

    public MutableLiveData<List<Product>> getAllProduct() {
        MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
        Call<List<Product>> call = mProductService.getAllProductsPage(mQueries);
        call.enqueue(getRetrofitCallback(mutableLiveData));
        return mutableLiveData;
    }

    public MutableLiveData<List<Product>> getProductsSubCategory(String name, String idCatrgory) {
        MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
        Call<List<Product>> call = mProductService.getProductsSubCategoires(mQueries, idCatrgory, name);
        call.enqueue(getRetrofitCallback(mutableLiveData));
        return mutableLiveData;
    }

    public MutableLiveData<List<CategoriesItem>> getCategory() {
         final MutableLiveData<List<CategoriesItem>> mListCategoryLiveData = new MutableLiveData<>();
        Call<CategoriesItem> call = mProductService.getProductCategory(mQueries);
        call.enqueue(new Callback<CategoriesItem>() {
            @Override
            public void onResponse(Call<CategoriesItem> call, Response<CategoriesItem> response) {
                if (response.isSuccessful()) {
                    CategoriesItem categoriesItem = response.body();
                    List<CategoriesItem> listCategory = new ArrayList<>();
                    listCategory.add(categoriesItem);
                    mListCategoryLiveData.setValue(listCategory);
                    //mCallbacks.onCategoryResponse(listCategory);
                }
            }

            @Override
            public void onFailure(Call<CategoriesItem> call, Throwable t) {

            }
        });
        return mListCategoryLiveData;
    }

    public MutableLiveData<List<CategoriesItem>> getAllCategory() {
        mListCategoryLiveData = new MutableLiveData<>();
        Call<List<CategoriesItem>> call = mProductService.getAllCategories(mQueries);
        call.enqueue(new Callback<List<CategoriesItem>>() {

            @Override
            public void onResponse(Call<List<CategoriesItem>> call, Response<List<CategoriesItem>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "successfulCategory");
                    List<CategoriesItem> listCategory = response.body();
                    mListCategoryLiveData.setValue(listCategory);
                    //mCallbacks.onCategoryResponse(listCategory);

                }
            }

            @Override
            public void onFailure(Call<List<CategoriesItem>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "FailedCategory " + t.getMessage());
            }
        });
        return mListCategoryLiveData;
    }

    public MutableLiveData<List<CategoriesItem>> getSubCategory(String categoryId) {
        final MutableLiveData<List<CategoriesItem>> mListCategoryLiveData = new MutableLiveData<>();
        Call<List<CategoriesItem>> call = mProductService.getSubCategories(categoryId);
        call.enqueue(new Callback<List<CategoriesItem>>() {

            @Override
            public void onResponse(Call<List<CategoriesItem>> call, Response<List<CategoriesItem>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "successfulCategory");
                    List<CategoriesItem> listCategory = response.body();
                    mListCategoryLiveData.setValue(listCategory);
                    //mCallbacks.onCategoryResponse(listCategory);
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesItem>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "FailedCategory " + t.getMessage());
            }
        });
        return mListCategoryLiveData;
    }


    private Callback<List<Product>> getRetrofitCallback(final MutableLiveData<List<Product>> listProMutableLiveData) {
        return new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful ");
                    List<Product> list = response.body();
                    listProMutableLiveData.setValue(list);
                    //mCallbacks.onProductResponse(mProductList);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Log.d(TAG_PRODUCT, "Failed " + t.getMessage());
            }
        };

    }

    public Callback<Customers> setCustomerwithRetrofit() {
         final MutableLiveData<Customers> mCustomerLiveData = new MutableLiveData<>();
        return new Callback<Customers>() {
            @Override
            public void onResponse(Call<Customers> call, Response<Customers> response) {
                mCustomerLiveData.setValue(response.body());
                //mCallbacks.onCustomerResponse(true);
            }

            @Override
            public void onFailure(Call<Customers> call, Throwable t) {
                //mCallbacks.onCustomerResponse(false);
            }
        };

    }

    private Callback<Product> getRetrofitProduct(final MutableLiveData<Product> productMutableLiveData) {
        return new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful Find Product");
                    Product product = response.body();
                    productMutableLiveData.setValue(product);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed not found product " + t.getMessage());
            }
        };
    }


    /*public interface ProductFetcherCallbacks {
        void onProductResponse(List<Product> productList);
        void onCategoryResponse(List<CategoriesItem> categoryList);
        void onCustomerResponse(boolean singupCustomer);

    }*/


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