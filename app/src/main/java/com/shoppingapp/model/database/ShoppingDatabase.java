package com.shoppingapp.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.shoppingapp.model.dao.CartDAO;
import com.shoppingapp.model.dao.ProductDAO;
import com.shoppingapp.model.dao.ProductThumbnailDAO;
import com.shoppingapp.model.entity.Cart;
import com.shoppingapp.model.entity.Product;
import com.shoppingapp.model.entity.ProductThumbnail;

@Database(entities = {ProductThumbnail.class, Product.class, Cart.class}, version = 1, exportSchema = false)
public abstract class ShoppingDatabase extends RoomDatabase {

    public abstract ProductThumbnailDAO getProductThumbnailDAO();
    public abstract ProductDAO getProductDAO();
    public abstract CartDAO getCartDAO();
}
