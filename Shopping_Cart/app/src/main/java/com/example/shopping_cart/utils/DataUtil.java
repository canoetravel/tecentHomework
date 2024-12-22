package com.example.shopping_cart.utils;

import com.example.shopping_cart.R;
import com.example.shopping_cart.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static List<ProductInfo> getList(int position){
        List<ProductInfo> mlist=new ArrayList<>();
        if(position==0){
            mlist.add(new ProductInfo(00,"华为手机","这是华为手机",4999, R.drawable.huawei));
            mlist.add(new ProductInfo(02,"小米手机","这是小米手机",4199,R.drawable.xiaomi));
            mlist.add(new ProductInfo(03,"OPPO手机","这是OPPO手机",3000,R.drawable.oppo));
            mlist.add(new ProductInfo(04,"苹果手机","这是苹果手机",6999,R.drawable.apple));
        }
        else if(position==1){
            mlist.add(new ProductInfo(01,"玫瑰花","这是玫瑰花",15, R.drawable.rose));
            mlist.add(new ProductInfo(02,"百合花","这是百合花",10,R.drawable.lily));
        }
        else if(position==2){
            mlist.add(new ProductInfo(01,"连衣裙","这是连衣裙",888,R.drawable.dress));
            mlist.add(new ProductInfo(02,"牛仔裤","这是牛仔裤",666,R.drawable.pants));
            mlist.add(new ProductInfo(03,"T恤衫","这是T恤衫",299,R.drawable.tshirt));
        }
        return mlist;
    }

}
