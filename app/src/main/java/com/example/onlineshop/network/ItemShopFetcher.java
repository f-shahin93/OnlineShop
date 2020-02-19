package com.example.onlineshop.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.ShoppingCart;
import com.example.onlineshop.model.category.Categories;
import com.example.onlineshop.model.customers.Customers;
import com.example.onlineshop.model.orders.Orders;
import com.example.onlineshop.model.review.Review;
import com.example.onlineshop.network.interfaces.ProductService;

import java.io.IOException;
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
    private MutableLiveData<List<Product>> mListNewestProMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mListPopularProMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mListMostPointProMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mAllProductListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListSearchMLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListShoppingCartMLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Categories>> mListSubCategoryLiveData;
    private MutableLiveData<Product> mProductLiveData;
    private MutableLiveData<List<Product>> mProductListSubCategMLiveData;
    private MutableLiveData<List<Review>> mProductReviewsListMLiveData;
    private MutableLiveData<Customers> mCustomerLiveData;
    private MutableLiveData<Boolean> mResultOrderPostLiveData;
    private String mTotalPageNumber;
    private List<Categories> mBasicCategoriesList;
    private List<Categories> mSubCategoriesList;
    private List<Product> mProductListShoppingCart = new ArrayList<>();
    private ResponseCallback mResponseCallback;


    public static ItemShopFetcher getInstance() {
        if (mInstance == null) {
            mInstance = new ItemShopFetcher();
        }
        return mInstance;
    }

    private ItemShopFetcher() {

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
        mQueries.remove("page");
        mQueries.remove("orderby");
        Call<Product> call = mProductService.getProduct(productId, mQueries);
        call.enqueue(getRetrofitProduct());
    }

    public void setCustomer(Customers customer) {
        mQueries.remove("page");
        mQueries.remove("orderby");
        Call<Customers> customersCall = mProductService.setCustomers(mQueries,customer);
        customersCall.enqueue(setCustomerwithRetrofit());
    }

    public MutableLiveData<Boolean> setOrders(Orders orders){
        mQueries.remove("page");
        mQueries.remove("orderby");
        mResultOrderPostLiveData = new MutableLiveData<>();
        mProductService.setOrder(mQueries,orders).enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful orders ");
                    mResultOrderPostLiveData.setValue(true);
                } else {
                    mResultOrderPostLiveData.setValue(false);
                    Log.d(TAG_PRODUCT, "is not Successful orders " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });

        return mResultOrderPostLiveData;
    }

    public MutableLiveData<List<Customers>> getCustomer(String email) {
        MutableLiveData<List<Customers>> mutableLiveData = new MutableLiveData<>();
        mQueries.remove("page");
        mQueries.remove("orderby");
        mQueries.put("email",email);
        mProductService.getCustomer(mQueries).enqueue(new Callback<List<Customers>>() {
            @Override
            public void onResponse(Call<List<Customers>> call, Response<List<Customers>> response) {
                if(response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Customers>> call, Throwable t) {

            }
        });

        return mutableLiveData;
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
        mProductService.getProductBody(mQueries).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> list = response.body();
                    mProductListSearchMLiveData.setValue(list);
                }
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
    }

    public MutableLiveData<List<Product>> getProductsSubCategoryByPage(String name, String idCatrgory, int page) {
        MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
        mQueries.remove("orderby");
        mQueries.put("page", String.valueOf(page));
        Call<List<Product>> call = mProductService.getProductsSubCategoires(mQueries, idCatrgory, name);
        call.enqueue(getRetrofitCallback(mutableLiveData));
        return mutableLiveData;
    }

    public List<Product> getProductListSync() throws IOException {
        mQueries.remove("page");
        mQueries.remove("orderby");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(mQueries);
        map.put("per_page", "1");
        Call<List<Product>> call = mProductService.getProductBody(map);
        return call.execute().body();
    }

    public void getOrderProductList(String status) {
        mQueries.remove("page");
        mQueries.put("orderby", status);
        mProductService.getProductBody(mQueries).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
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

        mProductService.getAllProductsByPage(mQueries).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
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

        mProductService.getAllProductsByPage(mQueries).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "successfulOrderProduct page");
                    List<Product> list = response.body();
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


    public void getAllCategory(String displayCatg, int parentCatg) {
        mListSubCategoryLiveData = new MutableLiveData<>();
        mQueries.remove("page");
        mQueries.remove("orderby");
        mProductService.getAllCategories(mQueries, displayCatg, String.valueOf(parentCatg))
                .enqueue(new Callback<List<Categories>>() {

                    @Override
                    public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG_PRODUCT, "successfulCategory");
                            List<Categories> listCategory = response.body();
                            if (parentCatg == 0) {
                                setBasicCategoriesList(listCategory);
                                mListCategoryLiveData.setValue(listCategory);
                            } else
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
        mCustomerLiveData = new MutableLiveData<>();
        return new Callback<Customers>() {
            @Override
            public void onResponse(Call<Customers> call, Response<Customers> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful Customer ");
                    mCustomerLiveData.setValue(response.body());
                } else{
                    Log.d(TAG_PRODUCT, "is not Successful Customer " + response.message());
                    mCustomerLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Customers> call, Throwable t) {
                mCustomerLiveData.setValue(null);
                Log.d(TAG_PRODUCT, "Failed " + t.getMessage());
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

    public void getProducstListByIdProduct(List<Integer> productIdList){
        mQueries.remove("page");
        mQueries.remove("orderby");
        mProductListShoppingCart = new ArrayList<>();
        mProductListShoppingCartMLiveData = new MutableLiveData<>();
        for (int productId : productIdList) {

            try {

                Product product = mProductService.getProduct(String.valueOf(productId), mQueries).execute().body();
                mProductListShoppingCart.add(product);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setProductListShoppingCart(){
        mProductListShoppingCartMLiveData.setValue(mProductListShoppingCart);
    }

    public void getProductReviewsList(String productId) {
        mQueries.remove("page");
        mQueries.remove("orderby");
        mProductReviewsListMLiveData = new MutableLiveData<>();
        Call<List<Review>> call = mProductService.getProductReviewsList(mQueries, productId);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_PRODUCT, "isSuccessful Find review");
                    mProductReviewsListMLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.d(TAG_PRODUCT, "Failed not found review " + t.getMessage());
            }
        });
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

    public MutableLiveData<List<Product>> getProductListShoppingCartMLiveData() {
        return mProductListShoppingCartMLiveData;
    }

    public void setProductListShoppingCartMLiveData(MutableLiveData<List<Product>> productListShoppingCartMLiveData) {
        mProductListShoppingCartMLiveData = productListShoppingCartMLiveData;
    }

    public List<Product> getProductListShoppingCart() {
        return mProductListShoppingCart;
    }

    public void setProductListShoppingCart(List<Product> productListShoppingCart) {
        mProductListShoppingCart = productListShoppingCart;
    }

    public MutableLiveData<List<Review>> getProductReviewsListMLiveData() {
        return mProductReviewsListMLiveData;
    }

    public String getTotalPageNumber() {
        return mTotalPageNumber;
    }

    public void setTotalPageNumber(String totalPageNumber) {
        mTotalPageNumber = totalPageNumber;
    }

    public void setCallback(ResponseCallback responseCallback) {
        mResponseCallback = responseCallback;
    }

    public interface ResponseCallback {
        void productListCartCallback(List<Product> productList);
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