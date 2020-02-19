package com.example.onlineshop.view.Adapter;


import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemListShoppingCartBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.DetailProViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductShoppingCartAdapter extends RecyclerView.Adapter<ProductShoppingCartAdapter.ShoppingCartViewHolder> {

    private Context mContext;
    private ItemListShoppingCartBinding mShoppingCartBinding;
    private List<Product> mProductList;

    public ProductShoppingCartAdapter(Context context, List<Product> list) {
        mContext = context;
        mProductList = list;
        setHasStableIds(true);
    }

    public void setListadapter(List<Product> list) {
        mProductList = list;
    }


    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mShoppingCartBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_list_shopping_cart, parent, false);
        return new ShoppingCartViewHolder(mShoppingCartBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {
        getItemViewType(mProductList.indexOf(mProductList.get(position)));
        holder.bind(mProductList.get(position));

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        private DetailProViewModel mDetailProViewModel;
        private Product mProductVh;
        private List<Integer> countProductSpinner;
        private ArrayAdapter mArrayAdapter;
        private int spItemSelected;

        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
            mDetailProViewModel = new ViewModelProvider((FragmentActivity) mContext).get(DetailProViewModel.class);

            setupSpinner();

        }

        private void setupSpinner() {
            countProductSpinner = new ArrayList<>();
            for (int i = 1; i < 11; i++) {
                Log.d("TagProduct","setListSpinner : "+i);
                countProductSpinner.add(i);
            }
            mArrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, countProductSpinner);
            mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mShoppingCartBinding.spinnerShoppingCart.setAdapter(mArrayAdapter);

            mShoppingCartBinding.spinnerShoppingCart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    spItemSelected = (int) adapterView.getItemAtPosition(position);
                    Log.d("TagProduct","spItemSelected : " + mProductVh.getName() +": "+ spItemSelected);
                    Log.d("TagProduct","OnItemSelectedSpinner : " + mProductVh.getName() +": "+ mProductVh.getCountProductInCart().getValue());
                    if (mProductVh.getCountProductInCart().getValue() != spItemSelected)
                        mDetailProViewModel.changeCountProductListCart(mProductVh, spItemSelected);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            mShoppingCartBinding.tvDeleteProductFromShoppingCart.setOnClickListener(view -> {
                mDetailProViewModel.showDeleteDialog(mProductVh, true);

//                    new AlertDialog.Builder(mContext)
//                            .setMessage("اطمینان دارید که این محصول حذف شود؟")
//                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    //ProductRepository.getInstance().deletePruductFromList(mProductVh);
//                                    mCallback.notifyChangedList(true, mProductVh);
//                                }
//                            })
//                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            })
//                            .create();
            });
        }

        public void bind(Product product) {
            mProductVh = product;
            Log.d("TagProduct", "OnbindCartViewHol : " + mProductVh.getName() + ": " + mProductVh.getCountProductInCart().getValue());
            mShoppingCartBinding.titleProductShoppingCart.setText(mProductVh.getName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mShoppingCartBinding.descriptionProductShoppingCart
                        .setText(Html.fromHtml(mProductVh.getShortDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                Spanned sp = Html.fromHtml(mProductVh.getShortDescription());
                mShoppingCartBinding.descriptionProductShoppingCart.setText(sp);
            }

            if (mProductVh.getCountProductInCart().getValue() != null) {
                Log.d("TagProduct", "OnbindSpinner : " + mProductVh.getName() + ": " + mProductVh.getCountProductInCart().getValue());
                mShoppingCartBinding.spinnerShoppingCart.setSelection(mProductVh.getCountProductInCart().getValue()-1);
            }

            if (mProductVh.getRegularPrice() == null || mProductVh.getRegularPrice().equals("")) {
                mShoppingCartBinding.totalPriceShoppingCart.setText(mProductVh.getPrice() + " تومان");
            } else {
                mShoppingCartBinding.totalPriceShoppingCart.setText(mProductVh.getRegularPrice() + " تومان");
            }

            if (mProductVh.getSalePrice() == null) {
                mShoppingCartBinding.finalPriceShoppingCart.setText(mProductVh.getPrice() + " تومان");
            } else {
                mShoppingCartBinding.finalPriceShoppingCart.setText(mProductVh.getSalePrice() + " تومان");
            }

            Picasso.with(mContext)
                    .load(mProductVh.getImages().get(0).getSrc())
                    .resize(100, 100)
                    .placeholder(R.drawable.place_holder_shopping_cart)
                    .into(mShoppingCartBinding.IvProductShoppingCart);
        }

    }

}
