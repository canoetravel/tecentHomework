package com.example.shopping_cart.entity;

public class Personinfo {
    public int id;
    public String phone;
    public String password;
    public String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Personinfo(String phone, String password, String email) {
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public Personinfo() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}




