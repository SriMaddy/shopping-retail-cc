package com.shoppingapp.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoppingapp.model.entity.ProductThumbnail;

import java.util.List;

@Dao
public interface ProductThumbnailDAO {

    @Query("SELECT * FROM product_thumbnail")
    LiveData<List<ProductThumbnail>> getAllProductThumbnails();

    @Insert
    long addProductThumbnail(ProductThumbnail productThumbnail);

    @Query("DELETE FROM product_thumbnail")
    void deleteAllProductThumbnails();
}
