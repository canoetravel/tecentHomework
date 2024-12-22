package com.example.shopping_cart.DBhelpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopping_cart.Orders;
import com.example.shopping_cart.entity.Baginfo;
import com.example.shopping_cart.entity.Orderinfo;
import com.example.shopping_cart.entity.Personinfo;


import java.util.ArrayList;
import java.util.List;

public class OrderDBhelper extends SQLiteOpenHelper {
    private static final String db_name="order.db";
    private static final int db_version=1;
    private static final String table_order="initial_table";
    private static OrderDBhelper mHelper=null;
    private static SQLiteDatabase MRDB=null,MWDB=null;

    private OrderDBhelper(Context context){
        super(context,db_name,null,db_version);
    }

    public static OrderDBhelper getInstance(Context context){
        if(mHelper==null){
            mHelper=new OrderDBhelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink(){
        if(MRDB==null||!MRDB.isOpen()){
            MRDB=mHelper.getReadableDatabase();
        }
        return MRDB;
    }

    public SQLiteDatabase openWriteLink(){
        if(MWDB==null||!MWDB.isOpen()){
            MWDB=mHelper.getWritableDatabase();
        }
        return MWDB;
    }

    public void closeLink(){
        if(MRDB!=null&&MRDB.isOpen()){
            MRDB.close();
            MRDB=null;
        }
        if(MWDB!=null&&MWDB.isOpen()){
            MWDB.close();
            MWDB=null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + table_order + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                " user_name TEXT NOT NULL," +
                " name TEXT NOT NULL," +
                " num Integer NOT NULL," +
                " total Integer NOT NULL," +
                " status Integer NOT NULL," +
                " image Integer NOT NULL," +
                " time text NOT NULL);";
        db.execSQL(sql);

    }//创建函数

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")
    public List<Orderinfo> queryForAll(String user_name){//查询对应用户所有商品
        String sql = "SELECT * FROM " + table_order + " WHERE user_name=?";
        List<Orderinfo> list=new ArrayList<>();
        Cursor cursor = MRDB.rawQuery(sql, new String[]{user_name});
        while(cursor.moveToNext()){
            Orderinfo info=new Orderinfo();
            info.name=cursor.getString(cursor.getColumnIndex("name"));
            info.num=cursor.getInt(cursor.getColumnIndex("num"));
            info.total=cursor.getInt(cursor.getColumnIndex("total"));
            info.image=cursor.getInt(cursor.getColumnIndex("image"));
            info.time=cursor.getString(cursor.getColumnIndex("time"));
            info.status=cursor.getInt(cursor.getColumnIndex("status"));
            list.add(info);
        }
        return list;
    }

    public void insertOrder(List<Orderinfo> list,String user_name){//将一件新商品加入购物车
        try{
            MWDB.beginTransaction();
            for(Orderinfo info:list){
                ContentValues values=new ContentValues();
                values.put("user_name",user_name);
                values.put("name",info.name);
                values.put("num",info.num);
                values.put("total",info.total);
                values.put("time",info.time);
                values.put("image",info.image);
                values.put("status",info.status);

                MWDB.insert(table_order,null,values);
            }


            MWDB.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MWDB.endTransaction();
        }
    }

    public void deleteinfo(String name,String user_name,String time){//删除对应用户对应商品
        //MWDB.delete(table_cart,"name=? and user_name=?", names,user_name);
        // 构建参数化查询
        String sql = "DELETE FROM "+table_order+" WHERE name=? AND user_name=? AND time=?";

        // 执行删除操作
        MWDB.execSQL(sql, new String[]{name, user_name,time});

    }

    @SuppressLint("Range")
    public void ChangeStatus(String name,String user_name,String time){
        Cursor cursor = MRDB.query(table_order, null, "name=? and user_name=? and time=?", new String[]{String.valueOf(name),user_name,time},null,null,null);
        if(cursor.moveToNext()){
            ContentValues values=new ContentValues();
            values.put("status",2);
            MWDB.update(table_order,values,"name=? and user_name=? and time=?",new String[]{String.valueOf(name),user_name,time});
        }


    }


}
