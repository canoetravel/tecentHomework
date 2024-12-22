package com.example.shopping_cart.entity;

import java.io.Serializable;

public class ProductInfo implements Serializable {
    private int id;
    private String name;
    private String message;
    private int price;
    private int image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public int getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public ProductInfo(int id, String name, String message, int price, int image) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.price = price;
        this.image = image;
    }
}
