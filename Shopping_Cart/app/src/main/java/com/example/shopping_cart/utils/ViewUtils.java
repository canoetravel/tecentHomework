package com.example.shopping_cart.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtils {
    public static void hideOneInputMethod(Activity act, View v){
        InputMethodManager imm=(InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

}
