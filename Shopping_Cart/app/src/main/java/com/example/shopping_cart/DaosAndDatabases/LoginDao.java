package com.example.shopping_cart.DaosAndDatabases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopping_cart.entity.Personinfo;
import com.example.shopping_cart.entity.RememberPersoninfo;
import com.example.shopping_cart.entity.RoomPersoninfo;

@Dao
public interface LoginDao {

    @Insert
    void insertOne(RoomPersoninfo roompersoninfo);

    @Query("select * from initial_table where phone=:phone")
    boolean IfPhoneRepeat(String phone);

    @Query("SELECT password FROM initial_table WHERE phone = :phone AND email = :email")
    String IfEmailCorrect(String phone,String email);//如果正确返回密码

    @Query("SELECT * FROM initial_table WHERE phone=:phone AND password=:password")
    boolean IfPasswordCorrect(String phone, String password);

    @Query("UPDATE initial_table SET password = :password WHERE phone = :phone")
    void updatePasswordByPhone(String phone, String password);

    @Query("update table_remember set phone=:phone and password=:password")
    void update_remember(String phone,String password);

    @Query("DELETE FROM table_remember")
    void delete_remember();

    @Query("select * from table_remember")
    RememberPersoninfo query_remember();

    @Insert
    void insert_remember(RememberPersoninfo rememberpersoninfo);

}













































