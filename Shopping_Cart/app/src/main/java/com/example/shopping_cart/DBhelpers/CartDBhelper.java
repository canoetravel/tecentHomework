package com.example.shopping_cart.DBhelpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.shopping_cart.entity.Baginfo;
import com.example.shopping_cart.entity.ProductInfo;


import java.util.ArrayList;
import java.util.List;

public class CartDBhelper extends SQLiteOpenHelper {
    private static final String db_name="cart.db";
    private static final int db_version=1;
    private static final String table_cart ="table_cart";
    private static final String TAG = "liu";
    private static CartDBhelper mHelper=null;
    private static SQLiteDatabase MRDB=null,MWDB=null;

    private CartDBhelper(Context context){
        super(context,db_name,null,db_version);
    }

    public static CartDBhelper getInstance(Context context){
        if(mHelper==null){
            mHelper=new CartDBhelper(context);
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
        Log.d(TAG, "创建了cart表");
        String sql = "CREATE TABLE IF NOT EXISTS " + table_cart + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                " user_name TEXT NOT NULL," +
                " name TEXT NOT NULL," +
                " price INTEGER NOT NULL," +
                " num INTEGER NOT NULL," +
                " image INTEGER NOT NULL," +
                " message text NOT NULL);";
        db.execSQL(sql);

    }//创建函数

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertcart(Baginfo info,String user_name){//将一件新商品加入购物车
        try{
            MWDB.beginTransaction();

            ContentValues values=new ContentValues();
            values.put("user_name",user_name);
            values.put("name",info.name);
            values.put("price",info.price);
            values.put("num",info.num);
            values.put("image",info.image);
            values.put("message",info.message);

            MWDB.insert(table_cart,null,values);

            MWDB.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MWDB.endTransaction();
        }
    }

    public void deleteAll(String user_name){
        MWDB.delete(table_cart,"user_name=?",new String[]{user_name});
    }

    public void deleteinfo(String name,String user_name){//删除对应用户对应商品
        //MWDB.delete(table_cart,"name=? and user_name=?", names,user_name);
        // 构建参数化查询
        String sql = "DELETE FROM table_cart WHERE name=? AND user_name=?";

        // 执行删除操作
        MWDB.execSQL(sql, new String[]{name, user_name});

    }

    @SuppressLint("Range")
    public List<Baginfo> queryForAll(String user_name){//查询对应用户所有商品
        String sql = "SELECT * FROM " + table_cart + " WHERE user_name=?";
        List<Baginfo> list=new ArrayList<>();
        Cursor cursor = MRDB.rawQuery(sql, new String[]{user_name});
        while(cursor.moveToNext()){
            Baginfo info=new Baginfo();
            info.name=cursor.getString(cursor.getColumnIndex("name"));
            info.price=cursor.getInt(cursor.getColumnIndex("price"));
            info.num=cursor.getInt(cursor.getColumnIndex("num"));
            info.image=cursor.getInt(cursor.getColumnIndex("image"));
            info.message=cursor.getString(cursor.getColumnIndex("message"));
            list.add(info);
        }
        return list;
    }

    @SuppressLint("Range")
    public void addToCart(ProductInfo productInfo,String user_name){//加入购物车统一操作，判断状态
        if(IfGoodInCart(productInfo.getName(),user_name)){//此商品已经在购物车中
            Cursor cursor = MRDB.query(table_cart, null, "name=? and user_name=?", new String[]{String.valueOf(productInfo.getName()),user_name},null,null,null);
            ContentValues values=new ContentValues();
            while(cursor.moveToNext()){
                //Log.d(TAG, cursor.getInt(cursor.getColumnIndex("num"))+1+"");
                values.put("num",cursor.getInt(cursor.getColumnIndex("num"))+1);
                MWDB.update(table_cart,values,"name=? and user_name=?",new String[]{String.valueOf(productInfo.getName()),user_name});
            }

        }else{//商品不在购物车里
            Baginfo info=new Baginfo();
            info.name=productInfo.getName();
            info.price=productInfo.getPrice();
            info.num=1;
            info.image=productInfo.getImage();
            info.message=productInfo.getMessage();
            insertcart(info,user_name);
        }
    }


    public boolean IfGoodInCart(String name,String user_name){//检查购物车中是否有此商品
        //Log.d("liu", MRDB==null?"为0":"不为0");
        Cursor cursor = MRDB.query(table_cart, null, "name=? and user_name=?", new String[]{String.valueOf(name),user_name},null,null,null);
        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }

    @SuppressLint("Range")
    public void MinusBagInfo(String name,String user_name){
        int num=-1;
        Cursor cursor = MRDB.query(table_cart, null, "name=? and user_name=?", new String[]{String.valueOf(name),user_name},null,null,null);
        if(cursor.moveToNext()){
            num=cursor.getInt(cursor.getColumnIndex("num"));
        }
        if(num==1){
            deleteinfo(name,user_name);
        }else if(num>1){
            ContentValues values=new ContentValues();
            values.put("num",num-1);
            MWDB.update(table_cart,values,"name=? and user_name=?",new String[]{String.valueOf(name),user_name});
        }
    }

    @SuppressLint("Range")
    public void AddBagInfo(String name,String user_name){
        int num=-1;
        Cursor cursor = MRDB.query(table_cart, null, "name=? and user_name=?", new String[]{String.valueOf(name),user_name},null,null,null);
        if(cursor.moveToNext()){
            num=cursor.getInt(cursor.getColumnIndex("num"));
        }
        if(num==-1) return;
        ContentValues values=new ContentValues();
        values.put("num",num+1);
        MWDB.update(table_cart,values,"name=? and user_name=?",new String[]{String.valueOf(name),user_name});

    }

    @SuppressLint("Range")
    public int getTotalPrice(String user_name){
        int ans=0;
        Cursor cursor = MRDB.query(table_cart, null, "user_name=?", new String[]{user_name},null,null,null);
        while(cursor.moveToNext()){
            int num=cursor.getInt(cursor.getColumnIndex("num")),price=cursor.getInt(cursor.getColumnIndex("price"));
            ans+=num*price;
        }
        return ans;
    }



//    public String IfEmailCorrect(String phone,String email){//找回是查看邮件是否正确
//        Cursor cursor = MRDB.query(table_cart, null, "email=?", new String[]{String.valueOf(email)},null,null,null);
//        while(cursor.moveToNext()){
//            if(phone.equals(cursor.getString(1))) return cursor.getString(2);
//        }
//        return null;
//    }
//
//    public boolean IfPasswordCorrect(String phone,String password){//登录时查看密码是否正确
//        //Log.d("liu", MRDB==null?"为0":"不为0");
//        Cursor cursor = MRDB.query(table_cart, null, "phone=?", new String[]{String.valueOf(phone)},null,null,null);
//        while(cursor.moveToNext()){
//            if(password.equals(cursor.getString(2))) return true;
//        }
//        return false;
//    }
}
