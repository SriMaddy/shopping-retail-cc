package com.shoppingapp.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoppingapp.model.entity.Cart;

import java.util.List;

@Dao
public interface CartDAO {

    @Query("SELECT * FROM cart")
    LiveData<List<Cart>> getAllProductsFromCart();

    @Insert
    long addProductToCart(Cart cart);

    @Query("DELETE FROM cart")
    void deleteAllProductsFromCart();

    @Query("DELETE FROM cart WHERE id = :id")
    void deleteProductFromCart(long id);

    @Query("SELECT * FROM cart WHERE product_id = :productId")
    LiveData<Cart> getCartByProductId(long productId);

    @Query("UPDATE cart SET count = :numberOfProduct WHERE product_id = :productId")
    void updateProductInCart(int numberOfProduct, long productId);
}
