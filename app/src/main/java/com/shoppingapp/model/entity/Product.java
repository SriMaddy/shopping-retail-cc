package com.shoppingapp.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.shoppingapp.application.ApplicationController;
import com.shoppingapp.util.BitmapUtil;

@Entity(tableName = "products")
public class Product extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "image")
    private int imageUrl;

    @ColumnInfo(name = "price")
    private double price;

    public Product(String name, String category, String type, int imageUrl, double price) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    protected Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        category = in.readString();
        type = in.readString();
        imageUrl = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bindable
    public int getImageUrl() {
        return imageUrl;
    }

    @BindingAdapter({"android:src"})
    public static void setImageUrl(ImageView imageView, int imageUrl) {
//        imageView.setImageResource(imageUrl);
        imageView.setImageBitmap(BitmapUtil.decodeSampledBitmapFromResource(ApplicationController.getAppInstance().getResources(), imageUrl));
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeInt(imageUrl);
        dest.writeDouble(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return getId() == product.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
