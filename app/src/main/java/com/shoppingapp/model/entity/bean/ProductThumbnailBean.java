package com.shoppingapp.model.entity.bean;

import com.shoppingapp.model.entity.ProductThumbnail;

import java.util.List;

public class ProductThumbnailBean {

    private List<ProductThumbnail> productThumbnails;

    public List<ProductThumbnail> getProductThumbnails() {
        return productThumbnails;
    }

    public void setProductThumbnails(List<ProductThumbnail> productThumbnails) {
        this.productThumbnails = productThumbnails;
    }
}
