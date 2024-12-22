package com.example.shopping_cart.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    public static void saveImage(String path, Bitmap bitmap){
        FileOutputStream fos=null;
        try{
            fos=new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        }catch(Exception e){
            Log.d("liu", "存储出问题了！！！");
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public static Bitmap openImage(String path) {
        Bitmap bitmap=null;
        FileInputStream fis=null;
        try{
            fis=new FileInputStream(path);
            bitmap= BitmapFactory.decodeStream(fis);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bitmap;
    }
}
