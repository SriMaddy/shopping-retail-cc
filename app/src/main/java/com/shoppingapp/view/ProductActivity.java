package com.shoppingapp.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shoppingapp.R;
import com.shoppingapp.application.ApplicationController;
import com.shoppingapp.model.entity.Product;
import com.shoppingapp.model.entity.ProductThumbnail;
import com.shoppingapp.model.repo.ShoppingRepo;
import com.shoppingapp.view.adapter.ProductsRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppingapp.util.AppConstants.KEY_SELECTED_PRODUCT_THUMBNAIL;

public class ProductActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_products)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cart_btn)
    ImageView cartBtn;

    private ProductsRecyclerAdapter mAdapter;
    private ProductThumbnail selectedProductThumbnail;

    private ShoppingRepo shoppingRepo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Log.i("ProductActivity", "called");

        ButterKnife.bind(this);

        shoppingRepo = ApplicationController.getAppInstance().getShoppingRepo();
        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey(KEY_SELECTED_PRODUCT_THUMBNAIL)) {
            selectedProductThumbnail = bundle.getParcelable(KEY_SELECTED_PRODUCT_THUMBNAIL);
        }

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(selectedProductThumbnail.getCategory());
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setRecyclerViewAttributes();
        setValues();
    }

    private void setRecyclerViewAttributes() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setValues() {
        shoppingRepo.getAllProducts(selectedProductThumbnail.getCategory(), selectedProductThumbnail.getType())
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable List<Product> products) {
                        setAdapter(products);
                    }
                });
    }

    private void setAdapter(List<Product> products) {
        mAdapter = new ProductsRecyclerAdapter(products);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @OnClick(R.id.cart_btn)
    public void onClick(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
