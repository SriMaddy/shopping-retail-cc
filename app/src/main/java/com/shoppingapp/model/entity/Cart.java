package com.shoppingapp.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.shoppingapp.BR;

@Entity(tableName = "cart")
public class Cart extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "product_id")
    private long productId;

    @ColumnInfo(name = "count")
    private int numberOfProduct;

    public Cart(long productId) {
        this.productId = productId;
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
        notifyPropertyChanged(BR.productId);
    }

    @Bindable
    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
        notifyPropertyChanged(BR.numberOfProduct);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;

        Cart cart = (Cart) o;

        return getProductId() == cart.getProductId();
    }

    @Override
    public int hashCode() {
        return (int) (getProductId() ^ (getProductId() >>> 32));
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", productId=" + productId +
                ", numberOfProduct=" + numberOfProduct +
                '}';
    }
}
