package com.example.onlineshop.network;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.airbnb.lottie.L;
import com.example.onlineshop.model.CategoriesItem;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.category.Categories;
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
    public static final String TAG_PRODUCT = "TagProduct";

    private static ItemShopFetcher mInstance;
    private Map<String, String> mQueries;
    private static Retrofit mRetrofit;
    private ProductService mProductService;
    private MutableLiveData<List<Categories>> mListCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Categories>> mListSubCategoryLiveData;
    private MutableLiveData<List<Product>> mListNewestProMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mListPopularProMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mListMostPointProMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductLiveData;
    private MutableLiveData<List<Product>> mAllProductListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListSubCategMLiveData;
    private MutableLiveData<List<Product>> mProductListSearchMLiveData = new MutableLiveData<>();
    private MutableLiveData<Customers> mCustomerLiveData = new MutableLiveData<>();
    private String mTotalPageNumber;
    private List<Categories> mBasicCategoriesList;
    private List<Categories> mSubCategoriesList;


    public static ItemShopFetcher getInstance() {
        if (mInstance == null) {
            mInstance = new ItemShopFetcher();
        }
        return mInstance;
    }

    private ItemShopFetcher() {

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


    public void getProductById(String productId) {
        //MutableLiveData<Product> productLiveData = new MutableLiveData<>();
        mQueries.remove("page");
        mQueries.remove("orderby");
        Call<Product> call = mProductService.getProduct(productId, mQueries);
        call.enqueue(getRetrofitProduct());
        //return productLiveData;
    }

    public void setCustomer(Customers customer) {
        Call<Customers> customersCall = mProductService.setCustomers(customer);
        customersCall.enqueue(setCustomerwithRetrofit());
    }

    public void getAllProduct() {
        mQueries.remove("page");
        mQueries.remove("orderby");
        Call<List<Product>> call = mProductService.getProductBody(mQueries);
        call.enqueue(getRetrofitCallback(mAllProductListMutableLiveData));
    }

    public void getProductListSearch(String querySearch) {
        mQueries.remove("page");
        mQueries.remove("orderby");
        mQueries.put("search", querySearch);
        Call<List<Product>> call = mProductService.getProductBody(mQueries);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> list = response.body();
                mProductListSearchMLiveData.setValue(list);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed " + t.getMessage());
            }
        });

    }

    public void getProductsSubCategory(String name, String idCatrgory) {
        mProductListSubCategMLiveData = new MutableLiveData<>();
        mQueries.remove("page");
        mQueries.remove("orderby");
        Call<List<Product>> call = mProductService.getProductsSubCategoires(mQueries, idCatrgory, name);
        call.enqueue(getRetrofitCallback(mProductListSubCategMLiveData));
        //return mutableLiveData;
    }

    public MutableLiveData<List<Product>> getProductsSubCategoryByPage(String name, String idCatrgory, int page) {
        MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
        mQueries.remove("orderby");
        mQueries.put("page", String.valueOf(page));
        Call<List<Product>> call = mProductService.getProductsSubCategoires(mQueries, idCatrgory, name);
        call.enqueue(getRetrofitCallback(mutableLiveData));
        return mutableLiveData;
    }

    public void getOrderProductList(String status) {
        mQueries.remove("page");
        mQueries.put("orderby", status);
        Call<List<Product>> call = mProductService.getProductBody(mQueries);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG_PRODUCT, "successfulOrderProduct");
                List<Product> list = response.body();
                setTotalPageNumber(response.raw().header("X-WP-TotalPages"));

                if (status.equals("date")) {
                    mListNewestProMutableLiveData.setValue(list);

                } else if (status.equals("popularity")) {
                    mListPopularProMutableLiveData.setValue(list);

                } else if (status.equals("rating")) {
                    mListMostPointProMutableLiveData.setValue(list);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<Product>> getOrderProductListByPage(String status, int pageNumber) {

        MutableLiveData<List<Product>> mAllProductListMLData = new MutableLiveData<>();
        mQueries.put("orderby", status);
        mQueries.put("page", String.valueOf(pageNumber));

        Call<List<Product>> call = mProductService.getAllProductsByPage(mQueries);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG_PRODUCT, "successfulOrderProduct page");
                List<Product> list = response.body();

                if (status.equals("date")) {
                    mAllProductListMLData.setValue(list);

                } else if (status.equals("popularity")) {
                    mAllProductListMLData.setValue(list);

                } else if (status.equals("rating")) {
                    mAllProductListMLData.setValue(list);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed page " + t.getMessage());
            }
        });

        return mAllProductListMLData;
    }

    public MutableLiveData<List<Product>> getAllProductListByPage(int pageNumber) {

        MutableLiveData<List<Product>> mAllProductListMLData = new MutableLiveData<>();
        mQueries.remove("orderby");
        mQueries.put("page", String.valueOf(pageNumber));

        Call<List<Product>> call = mProductService.getAllProductsByPage(mQueries);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG_PRODUCT, "successfulOrderProduct page");
                List<Product> list = response.body();
                mAllProductListMLData.setValue(list);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed page " + t.getMessage());
            }
        });

        return mAllProductListMLData;
    }


    public void getAllCategory(String displayCatg ,int parentCatg) {
        mListSubCategoryLiveData = new MutableLiveData<>();
        mQueries.remove("page");
        mQueries.remove("orderby");
        Call<List<Categories>> call = mProductService.getAllCategories(mQueries ,displayCatg,String.valueOf(parentCatg));
        call.enqueue(new Callback<List<Categories>>() {

            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "successfulCategory");
                    List<Categories> listCategory = response.body();
                    if(parentCatg == 0){
                        setBasicCategoriesList(listCategory);
                        mListCategoryLiveData.setValue(listCategory);
                    }else
                        mListSubCategoryLiveData.setValue(listCategory);
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "FailedCategory " + t.getMessage());
            }
        });
    }

    private Callback<List<Product>> getRetrofitCallback(MutableLiveData<List<Product>> listProMutableLiveData) {
        return new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful ");
                    List<Product> list = response.body();
                    listProMutableLiveData.setValue(list);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed " + t.getMessage());
            }
        };
    }

    public Callback<Customers> setCustomerwithRetrofit() {
        MutableLiveData<Customers> mCustomerLiveData = new MutableLiveData<>();
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

    private Callback<Product> getRetrofitProduct() {
        mProductLiveData = new MutableLiveData<>();
        return new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful Find Product");
                    Product product = response.body();
                    mProductLiveData.setValue(product);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed not found product " + t.getMessage());
            }
        };
    }

    public MutableLiveData<List<Categories>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

    public MutableLiveData<List<Categories>> getListSubCategoryLiveData() {
        return mListSubCategoryLiveData;
    }

    public MutableLiveData<List<Product>> getListNewestProMutableLiveData() {
        return mListNewestProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListPopularProMutableLiveData() {
        return mListPopularProMutableLiveData;
    }

    public MutableLiveData<List<Product>> getListMostPointProMutableLiveData() {
        return mListMostPointProMutableLiveData;
    }

    public MutableLiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }

    public MutableLiveData<List<Product>> getAllProductListMutableLiveData() {
        return mAllProductListMutableLiveData;
    }

    public MutableLiveData<List<Product>> getProductListSubCategMLiveData() {
        return mProductListSubCategMLiveData;
    }

    public MutableLiveData<Customers> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public MutableLiveData<List<Product>> getProductListSearchMLiveData() {
        return mProductListSearchMLiveData;
    }

    public List<Categories> getBasicCategoriesList() {
        return mBasicCategoriesList;
    }

    public void setBasicCategoriesList(List<Categories> basicCategoriesList) {
        mBasicCategoriesList = basicCategoriesList;
    }

    public List<Categories> getSubCategoriesList() {
        return mSubCategoriesList;
    }

    public void setSubCategoriesList(List<Categories> subCategoriesList) {
        mSubCategoriesList = subCategoriesList;
    }

    public String getTotalPageNumber() {
        return mTotalPageNumber;
    }

    public void setTotalPageNumber(String totalPageNumber) {
        mTotalPageNumber = totalPageNumber;
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