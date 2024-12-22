package com.example.shopping_cart.DaosAndDatabases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.shopping_cart.entity.RememberPersoninfo;
import com.example.shopping_cart.entity.RoomPersoninfo;

@Database(entities = {RoomPersoninfo.class, RememberPersoninfo.class},version = 1,exportSchema = true)
public abstract class LoginDatabase extends RoomDatabase {
    public abstract LoginDao loginDao();

}
