package com.example.schoolfinal2023;

public class ProductItem {
    private String imageUrl;
    private String title;
    private String barcode;

    public ProductItem(String imageUrl, String title, String barcode) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.barcode = barcode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return barcode;
    }

    public void setDescription(String barcode) {
        this.barcode = barcode;
    }
}
