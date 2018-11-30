package com.shoppingapp.model.repo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.shoppingapp.application.ApplicationController;
import com.shoppingapp.model.database.ShoppingDatabase;
import com.shoppingapp.model.entity.Cart;
import com.shoppingapp.model.entity.Product;
import com.shoppingapp.model.entity.ProductThumbnail;
import com.shoppingapp.util.SharedPreference;

import java.util.List;

public class ShoppingRepo {

    private static final String DB_NAME = "shopping_db";

    private ShoppingDatabase shoppingDatabase;

    public ShoppingRepo(Context context) {
        shoppingDatabase = Room.databaseBuilder(context, ShoppingDatabase.class, DB_NAME).build();
    }

    @SuppressLint("StaticFieldLeak")
    private class ProductThumbnailAddAsyncTask extends AsyncTask<ProductThumbnail, Void, Void> {

        @Override
        protected Void doInBackground(ProductThumbnail... productThumbnails) {
            ProductThumbnail productThumbnail = productThumbnails[0];
            long result = shoppingDatabase.getProductThumbnailDAO().addProductThumbnail(productThumbnail);
            Log.i("ProductThumbnailAdd", result + "");
            return null;
        }
    }

    public void addProductThumbnail(final ProductThumbnail productThumbnail) {
        new ProductThumbnailAddAsyncTask().execute(productThumbnail);
    }

    public LiveData<List<ProductThumbnail>> getAllProductThumbnails() {
        return shoppingDatabase.getProductThumbnailDAO().getAllProductThumbnails();
    }

    public LiveData<Product> getProductById(long id) {
        return shoppingDatabase.getProductDAO().getProductById(id);
    }

    @SuppressLint("StaticFieldLeak")
    private class ProductThumbnailDeleteAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            shoppingDatabase.getProductThumbnailDAO().deleteAllProductThumbnails();
            return null;
        }
    }

    public void deleteAllProductThumbnails() {
        new ProductThumbnailDeleteAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class ProductAddAsyncTask extends AsyncTask<Product, Void, Void> {

        @Override
        protected Void doInBackground(Product... products) {
            Product product = products[0];
            long result = shoppingDatabase.getProductDAO().addProduct(product);
            Log.i("ProductAddResult", result + "");
            return null;
        }
    }

    public void addProduct(Product product) {
        new ProductAddAsyncTask().execute(product);
    }

    public LiveData<List<Product>> getAllProducts(String category, String type) {
        return shoppingDatabase.getProductDAO().getAllProducts(category, type);
    }

    @SuppressLint("StaticFieldLeak")
    private class CartAddAsyncTask extends AsyncTask<Cart, Void, Void> {

        @Override
        protected Void doInBackground(Cart... carts) {
            Cart cart = carts[0];
            long result = shoppingDatabase.getCartDAO().addProductToCart(cart);
            Log.i("CartAddResult", result + "");
            return null;
        }
    }

    public void addProductInCart(Cart cart) {
//        return shoppingDatabase.getCartDAO().addProductToCart(cart);
        new CartAddAsyncTask().execute(cart);
    }

    public LiveData<List<Cart>> getAllProductsFromCart() {
        return shoppingDatabase.getCartDAO().getAllProductsFromCart();
    }

    @SuppressLint("StaticFieldLeak")
    private class CartDeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            shoppingDatabase.getCartDAO().deleteAllProductsFromCart();
            return null;
        }
    }

    public void deleteAllProductsFromCart() {
        new CartDeleteAllAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class CartDeleteOneAsyncTask extends AsyncTask<Cart, Void, Void> {

        @Override
        protected Void doInBackground(Cart... carts) {
            Cart cart = carts[0];
            shoppingDatabase.getCartDAO().deleteProductFromCart(cart.getId());
            Log.i("deletedCartInDB", cart.toString());
            return null;
        }
    }

    public void deleteProductFromCart(Cart cart) {
//        return shoppingDatabase.getCartDAO().deleteProductFromCart(cart.getId());
        new CartDeleteOneAsyncTask().execute(cart);
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateCartAsyncTask extends AsyncTask<Cart, Void, Cart> {

        @Override
        protected Cart doInBackground(Cart... carts) {
            Cart cart = carts[0];
            shoppingDatabase.getCartDAO().updateProductInCart(cart.getNumberOfProduct(), cart.getProductId());
            Log.i("updatedCartInDB", cart.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Cart cart) {
            SharedPreference sharedPreference = ApplicationController.getAppInstance().getSharedPreference();
            List<Cart> carts = sharedPreference.getCart(ApplicationController.getAppInstance());
            carts.add(cart);
            sharedPreference.saveCart(ApplicationController.getAppInstance(), carts);
            super.onPostExecute(cart);
        }
    }

    public void updateProductInCart(Cart cart) {
        new UpdateCartAsyncTask().execute(cart);
//        shoppingDatabase.getCartDAO().updateProductInCart(cart.getNumberOfProduct(), cart.getProductId());
    }

    @SuppressLint("StaticFieldLeak")
    private class CartGetAsyncTask extends AsyncTask<Long, Void, Cart> {

        @Override
        protected Cart doInBackground(Long... longs) {
            long productId = longs[0];
            shoppingDatabase.getCartDAO().getCartByProductId(productId);
            return null;
        }

        @Override
        protected void onPostExecute(Cart cart) {
            int count = cart.getNumberOfProduct();
            cart.setNumberOfProduct(++count);
            updateProductInCart(cart);
            super.onPostExecute(cart);
        }
    }

    public LiveData<Cart> getCartByProductId(long productId) {
        return shoppingDatabase.getCartDAO().getCartByProductId(productId);
//        new CartGetAsyncTask().execute(productId);
    }
}
