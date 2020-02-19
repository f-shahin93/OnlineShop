package com.example.onlineshop.view.Adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemListProductCatogoryBinding;
import com.example.onlineshop.databinding.ItemReviewProductListBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.review.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.RevieViewHolder> {

    private Context mContext;
    private List<Review> mList;
    private ItemReviewProductListBinding mItemReviewBinding;

    public ReviewAdapter(Context context, List<Review> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RevieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemReviewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_review_product_list, parent, false);
        return new RevieViewHolder(mItemReviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RevieViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RevieViewHolder extends RecyclerView.ViewHolder {

        private Review mReviewVh;
        private ItemReviewProductListBinding mBinding;

        public RevieViewHolder(ItemReviewProductListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Review review) {
            mReviewVh = review;
            mBinding.tvNameUserCommentReview.setText(review.getReviewer());
            mBinding.tvDateCommentReview.setText(review.getDateCreated());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                mBinding.tvTextReview.setText(Html.fromHtml(review.getReview(), Html.FROM_HTML_MODE_LEGACY));
            else {
                Spanned sp = Html.fromHtml(review.getReview());
                mBinding.tvTextReview.setText(sp);
            }

        }
    }
}
