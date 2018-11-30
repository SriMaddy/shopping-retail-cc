package com.shoppingapp.model.entity.bean;

import com.shoppingapp.model.entity.Cart;

import java.util.List;

public class CartBean {

    private List<Cart> carts;

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
