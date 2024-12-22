package com.example.shopping_cart.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_remember")
public class RememberPersoninfo {
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

    public RememberPersoninfo(int id, @NonNull String phone, @NonNull String password) {
        this.id = id;
        this.phone = phone;
        this.password = password;
    }

    public RememberPersoninfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
