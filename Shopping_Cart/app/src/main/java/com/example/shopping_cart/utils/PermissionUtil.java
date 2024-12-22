package com.example.shopping_cart.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public static boolean checkPermission(Activity act,String[] permissions,int requestCode){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int check= PackageManager.PERMISSION_GRANTED;
            for(String permission:permissions){
                check= ContextCompat.checkSelfPermission(act,permission);

                if(check!=PackageManager.PERMISSION_GRANTED){

                    break;
                }
            }
            if(check!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(act,permissions,requestCode);
                return false;
            }
        }
        return true;
    }

    public static boolean checkGrant(int[] grantResults) {
        if(grantResults!=null){
            for(int grant:grantResults){
                if(grant!=PackageManager.PERMISSION_GRANTED) return false;
            }
            return true;
        }
        return false;
    }
}
