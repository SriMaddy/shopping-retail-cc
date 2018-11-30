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

@Entity(tableName = "product_thumbnail")
public class ProductThumbnail extends BaseObservable implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "image")
    private int productThumbnailImage;

    public ProductThumbnail(String category, String type, int productThumbnailImage) {
        this.category = category;
        this.type = type;
        this.productThumbnailImage = productThumbnailImage;
    }

    protected ProductThumbnail(Parcel in) {
        id = in.readLong();
        category = in.readString();
        type = in.readString();
        productThumbnailImage = in.readInt();
    }

    public static final Creator<ProductThumbnail> CREATOR = new Creator<ProductThumbnail>() {
        @Override
        public ProductThumbnail createFromParcel(Parcel in) {
            return new ProductThumbnail(in);
        }

        @Override
        public ProductThumbnail[] newArray(int size) {
            return new ProductThumbnail[size];
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
    public int getProductThumbnailImage() {
        return productThumbnailImage;
    }

    @BindingAdapter({"android:src"})
    public static void setProductThumbnailImage(ImageView imageView, int productThumbnailImage) {
        imageView.setImageBitmap(BitmapUtil.decodeSampledBitmapFromResource(ApplicationController.getAppInstance().getResources(), productThumbnailImage));
//        imageView.setImageResource(productThumbnailImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeInt(productThumbnailImage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductThumbnail)) return false;

        ProductThumbnail that = (ProductThumbnail) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
