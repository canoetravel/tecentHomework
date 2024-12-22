package com.example.shopping_cart.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "initial_table")
public class RoomPersoninfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    @NonNull
    public int id;

    @ColumnInfo(name = "phone")
    @NonNull
    public String phone;

    @ColumnInfo(name = "password")
    @NonNull
    public String password;

    @ColumnInfo(name = "email")
    @NonNull
    public String email;

    public RoomPersoninfo(int id, String phone, String password, String email) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public RoomPersoninfo() {
    }

    public RoomPersoninfo(@NonNull String phone, @NonNull String password, @NonNull String email) {
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
