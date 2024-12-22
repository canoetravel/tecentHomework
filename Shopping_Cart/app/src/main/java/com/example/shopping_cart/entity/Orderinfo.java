package com.example.shopping_cart.entity;

public class Orderinfo {
    public String name;
    public int num;
    public int total;
    public int image;
    public String time;
    public int status;

    public Orderinfo(String name, int num, int total, int image, String time, int status) {
        this.name = name;
        this.num = num;
        this.total = total;
        this.image = image;
        this.time = time;
        this.status = status;
    }

    public Orderinfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
