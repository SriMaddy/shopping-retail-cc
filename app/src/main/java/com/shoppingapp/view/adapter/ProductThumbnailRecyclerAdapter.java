package com.shoppingapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shoppingapp.BR;
import com.shoppingapp.R;
import com.shoppingapp.model.entity.ProductThumbnail;
import com.shoppingapp.view.ProductActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppingapp.util.AppConstants.KEY_SELECTED_PRODUCT_THUMBNAIL;

public class ProductThumbnailRecyclerAdapter extends RecyclerView.Adapter<ProductThumbnailRecyclerAdapter.ViewHolder> {

    private List<ProductThumbnail> productThumbnails;

    public ProductThumbnailRecyclerAdapter(List<ProductThumbnail> productThumbnails) {
        this.productThumbnails = productThumbnails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.product_thumbnail_grid_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductThumbnail productThumbnail = productThumbnails.get(position);
        holder.bind(productThumbnail);
    }

    @Override
    public int getItemCount() {
        return productThumbnails.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;
        private ProductThumbnail productThumbnail;

        @BindView(R.id.product_img)
        ImageView imageView;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        void bind(ProductThumbnail productThumbnail) {
            this.binding.setVariable(BR.productThumbNail, productThumbnail);
            this.productThumbnail = productThumbnail;
        }

        @OnClick({R.id.product_img})
        public void onClick(View v) {
            Context context = binding.getRoot().getContext();
            Intent intent = new Intent(context, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_SELECTED_PRODUCT_THUMBNAIL, productThumbnail);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
