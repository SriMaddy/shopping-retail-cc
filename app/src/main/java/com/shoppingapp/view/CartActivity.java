package com.shoppingapp.view;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppingapp.R;
import com.shoppingapp.application.ApplicationController;
import com.shoppingapp.listener.SwipeToDeleteCallback;
import com.shoppingapp.model.entity.Cart;
import com.shoppingapp.model.entity.Product;
import com.shoppingapp.model.repo.ShoppingRepo;
import com.shoppingapp.util.SharedPreference;
import com.shoppingapp.view.adapter.CartRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity /*implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener */{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view_cart)
    RecyclerView mRecyclerView;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.no_items_txt_view)
    TextView noItemsTxtView;

    @BindView(R.id.total_price_btn)
    Button btnTotalPrice;

    @BindView(R.id.cart_btn)
    ImageView cartBtn;

    private CartRecyclerAdapter mAdapter;
    private ShoppingRepo shoppingRepo;
    private List<Product> products;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);

        toolbar.setTitle("Cart");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cartBtn.setVisibility(View.GONE);

        shoppingRepo = ApplicationController.getAppInstance().getShoppingRepo();

        setRecyclerViewAttributes();
        setValues();
        enableSwipeToDelete();
    }

    private void enableSwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final Product product = products.get(position);
                final String itemToDelete = product.getName();

                Log.i("removedPos", position + "");
                mAdapter.removeProductFromCart(product);

                Snackbar snackbar = Snackbar.
                        make(coordinatorLayout, itemToDelete + " was removed from cart.", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void setRecyclerViewAttributes() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setValues() {
        products = new ArrayList<>();
        shoppingRepo.getAllProductsFromCart()
                .observe(this, new Observer<List<Cart>>() {
                    @Override
                    public void onChanged(@Nullable final List<Cart> carts) {
                        if(carts != null && carts.size() > 0) {
                            noItemsTxtView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            btnTotalPrice.setVisibility(View.VISIBLE);
                            for(Cart cart : carts) {
                                Log.i("cartId", cart.getId() + "," + cart.getProductId() + "," + cart.getNumberOfProduct());
                                shoppingRepo.getProductById(cart.getProductId())
                                .observe(CartActivity.this, new Observer<Product>() {
                                    @Override
                                    public void onChanged(@Nullable Product product) {
                                        if(product != null) {
                                            if(!products.contains(product)) {
                                                products.add(product);
                                                setAdapters(products, carts);
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.i("productsInCart", "Empty");
                            noItemsTxtView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            btnTotalPrice.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void setAdapters(List<Product> products, List<Cart> carts) {
        Log.i("ProductSizeCart", products.size() + "");
        mAdapter = new CartRecyclerAdapter(this, products, carts);
        mRecyclerView.setAdapter(mAdapter);

        double totalPrice = 0.0;
        for(Product product : products) {
            for(Cart cart : carts) {
                if(cart.getProductId() == product.getId()) {
                    totalPrice = totalPrice + (cart.getNumberOfProduct() * product.getPrice());
                }
            }
        }

        btnTotalPrice.setText("Total Price : " + totalPrice);

        SharedPreference sharedPreference = ApplicationController.getAppInstance().getSharedPreference();
        sharedPreference.saveCart(this, carts);
    }

    public void removeProductFromCart(Product product) {
        SharedPreference sharedPreference = ApplicationController.getAppInstance().getSharedPreference();
        List<Cart> carts = sharedPreference.getCart(this);

        Log.i("cartsInRemove", carts.toString());

        Cart cartFound = null;
        for(Cart cart : carts) {
            if(cart != null) {
                if (cart.getProductId() == product.getId()) {
                    cartFound = cart;
                    break;
                }
            }
        }

        boolean result = carts.remove(cartFound);
        Log.i("deletedCart", cartFound.toString() + result);
        sharedPreference.saveCart(this, carts);
        shoppingRepo.deleteProductFromCart(cartFound);
    }
}
