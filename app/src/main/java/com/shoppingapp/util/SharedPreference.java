package com.shoppingapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shoppingapp.model.entity.Cart;
import com.shoppingapp.model.entity.ProductThumbnail;
import com.shoppingapp.model.entity.bean.CartBean;
import com.shoppingapp.model.entity.bean.ProductThumbnailBean;

import java.util.List;

public class SharedPreference {

    private static final String PREF_NAME = "shopping_pref";

    private static final String KEY_CART = "cart";
    private static final String KEY_PRODUCT_THUMBNAIL = "productThumbnail";

    public void saveCart(Context context, List<Cart> carts) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        CartBean cartBean = new CartBean();
        cartBean.setCarts(carts);

        Gson gson = new Gson();
        String cartStr = gson.toJson(cartBean, CartBean.class);

        editor.putString(KEY_CART, cartStr);
        editor.apply();
    }

    public List<Cart> getCart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String cartStr = sharedPreferences.getString(KEY_CART, null);

        if(cartStr != null) {
            Gson gson = new Gson();
            CartBean cartBean = gson.fromJson(cartStr, CartBean.class);
            return cartBean.getCarts();
        }
        return null;
    }

    public void saveProductThumbnails(Context context, List<ProductThumbnail> productThumbnails) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ProductThumbnailBean productThumbnailBean = new ProductThumbnailBean();
        productThumbnailBean.setProductThumbnails(productThumbnails);

        Gson gson = new Gson();
        String productThumbnailStr = gson.toJson(productThumbnailBean, ProductThumbnailBean.class);

        editor.putString(KEY_PRODUCT_THUMBNAIL, productThumbnailStr);
        editor.apply();
    }

    public List<ProductThumbnail> getProductThumbnails(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String productThumbnailStr = sharedPreferences.getString(KEY_PRODUCT_THUMBNAIL, null);
        if(productThumbnailStr != null) {
            Gson gson = new Gson();
            ProductThumbnailBean productThumbnailBean = gson.fromJson(productThumbnailStr, ProductThumbnailBean.class);
            return productThumbnailBean.getProductThumbnails();
        }
        return null;
    }
}
