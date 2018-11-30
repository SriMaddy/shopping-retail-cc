package com.shoppingapp.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppingapp.R;
import com.shoppingapp.application.ApplicationController;
import com.shoppingapp.databinding.ActivityProductDetailBinding;
import com.shoppingapp.model.entity.Cart;
import com.shoppingapp.model.entity.Product;
import com.shoppingapp.model.repo.ShoppingRepo;
import com.shoppingapp.util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppingapp.util.AppConstants.KEY_SELECTED_PRODUCT;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.product_img)
    ImageView productImg;

    @BindView(R.id.product_price_txt_view)
    TextView productPriceTxtView;

    @BindView(R.id.cart_btn)
    ImageView cartBtn;

    @BindView(R.id.add_cart_btn)
    Button btnAddToCart;

    private Product selectedProduct;
    private ShoppingRepo shoppingRepo;

    private List<Cart> carts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProductDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey(KEY_SELECTED_PRODUCT)) {
            selectedProduct = bundle.getParcelable(KEY_SELECTED_PRODUCT);
        }

        binding.setProduct(selectedProduct);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(selectedProduct.getName());
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        shoppingRepo = ApplicationController.getAppInstance().getShoppingRepo();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @OnClick(R.id.add_cart_btn)
    public void onClick(View view) {
        final SharedPreference sharedPreference = ApplicationController.getAppInstance().getSharedPreference();
        carts = sharedPreference.getCart(this);

        final Cart cart = new Cart(selectedProduct.getId());

        if(shoppingRepo == null) {
            shoppingRepo = new ShoppingRepo(this);
        }

        if(carts == null) {
            cart.setNumberOfProduct(1);
            shoppingRepo.addProductInCart(cart);

            carts = new ArrayList<>();
            carts.add(cart);
            sharedPreference.saveCart(ProductDetailActivity.this, carts);

            goCart();
        } else {
            Observer<Cart> cartObserver = new Observer<Cart>() {
                @Override
                public void onChanged(@Nullable Cart cart) {
                    int numberOfProduct = cart.getNumberOfProduct();
                    cart.setNumberOfProduct(++numberOfProduct);
                    if(shoppingRepo != null) {
                        shoppingRepo.getCartByProductId(cart.getProductId()).removeObserver(this);
                        shoppingRepo.updateProductInCart(cart);
                        shoppingRepo = null;
                    }

                    goCart();
                    return;
                }
            };

            if(carts.contains(cart)) {
                shoppingRepo.getCartByProductId(cart.getProductId()).observe(ProductDetailActivity.this, cartObserver);
            } else {
                cart.setNumberOfProduct(1);
                shoppingRepo.addProductInCart(cart);

                carts.add(cart);
                sharedPreference.saveCart(ProductDetailActivity.this, carts);

                goCart();
            }
        }
    }

    @OnClick(R.id.cart_btn)
    public void goCart() {
        Intent intent = new Intent(this, CartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
