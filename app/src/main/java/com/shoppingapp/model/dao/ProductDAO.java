package com.shoppingapp.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoppingapp.model.entity.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM products WHERE category  = :category AND type = :type")
    LiveData<List<Product>> getAllProducts(String category, String type);

    @Insert
    long addProduct(Product product);

    @Query("SELECT * FROM products WHERE id = :id")
    LiveData<Product> getProductById(long id);
}
