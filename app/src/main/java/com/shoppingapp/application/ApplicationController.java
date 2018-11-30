package com.shoppingapp.application;

import android.app.Application;

import com.shoppingapp.model.repo.ShoppingRepo;
import com.shoppingapp.util.SharedPreference;

public class ApplicationController extends Application {

    private static ApplicationController appInstance;
    private static ShoppingRepo shoppingRepo;
    private static SharedPreference sharedPreference;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static ApplicationController getAppInstance() {
        return appInstance;
    }

    public ShoppingRepo getShoppingRepo() {
        if(shoppingRepo == null) {
            shoppingRepo = new ShoppingRepo(getApplicationContext());
        }
        return shoppingRepo;
    }

    public SharedPreference getSharedPreference() {
        if(sharedPreference == null) {
            sharedPreference = new SharedPreference();
        }
        return sharedPreference;
    }
}
