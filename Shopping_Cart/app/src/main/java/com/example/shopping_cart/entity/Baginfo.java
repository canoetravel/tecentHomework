package com.example.shopping_cart.entity;

public class Baginfo {
    public String name;
    public int price;
    public int num;
    public int image;
    public String message;

    public Baginfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Baginfo(String name, int price, int num, int image, String message) {
        this.name = name;
        this.price = price;
        this.num = num;
        this.image = image;
        this.message = message;
    }
}

