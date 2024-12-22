package com.example.shopping_cart.fragments;


import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopping_cart.Adapters.CartAdapter;
import com.example.shopping_cart.DBhelpers.CartDBhelper;
import com.example.shopping_cart.DBhelpers.OrderDBhelper;
import com.example.shopping_cart.R;
import com.example.shopping_cart.entity.Baginfo;
import com.example.shopping_cart.entity.Orderinfo;
import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CartFragment extends Fragment {

    private static final String TAG = "liu";
    private View rootview;
    private TextView txt_cart_empty;
    private RecyclerView ry_cart;
    private TextView txt_cart_total;
    private CartDBhelper mhelper;
    private List<Baginfo> mlist=new ArrayList<>();
    private CartAdapter adapter;
    private SharedPreferences msharedPreferences;
    private String phonenum= aaa.phonenum;
    private Button btn_cart_apply;
    private OrderDBhelper orderDBhelper;
    private String user_name=aaa.phonenum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_cart, container, false);
        txt_cart_empty = rootview.findViewById(R.id.txt_cart_empty);
        ry_cart = rootview.findViewById(R.id.ry_cart);
        txt_cart_total = rootview.findViewById(R.id.txt_cart_total);
//        msharedPreferences = getContext().getSharedPreferences("password_remember",MODE_PRIVATE);
//        phonenum=msharedPreferences.getString("get_phonenum","");
        btn_cart_apply = rootview.findViewById(R.id.btn_cart_apply);
        orderDBhelper = OrderDBhelper.getInstance(getContext());
        orderDBhelper.openWriteLink();
        orderDBhelper.openReadLink();

        mhelper = CartDBhelper.getInstance(getContext());
        mhelper.openReadLink();
        mhelper.openWriteLink();
        Log.d(TAG, "table-cart已创建");
        btn_cart_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("你确定要提交订单吗");
                builder.setPositiveButton("是的",((dialog, which) -> {
                    List<Orderinfo> list=new ArrayList<>();
                    for(Baginfo info:mlist){
                        Orderinfo orderinfo=new Orderinfo();
                        orderinfo.name=info.name;
                        orderinfo.status=1;
                        orderinfo.num=info.num;
                        orderinfo.total=info.price*info.num;
                        orderinfo.image=info.image;
                        orderinfo.time= TimeUtil.getCurrentTime();
                        list.add(orderinfo);
                    }
                    orderDBhelper.insertOrder(list,user_name);
                    mhelper.deleteAll(user_name);
                    updateCartList();
                }));
                builder.setNegativeButton("再考虑一下",((dialog, which) -> {

                }));
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d("liu", "重新加载购物车");

        mlist=mhelper.queryForAll(phonenum);
        txt_cart_total.setText("总价："+mhelper.getTotalPrice(phonenum)+"元");
        if (mlist.size() > 0) {
            txt_cart_total.setVisibility(View.VISIBLE);
            txt_cart_empty.setVisibility(View.GONE);
        }
        else{
            txt_cart_total.setVisibility(View.GONE);
            txt_cart_empty.setVisibility(View.VISIBLE);
        }
        adapter = new CartAdapter(mlist);
        ry_cart.setAdapter(adapter);
        updateCartList();
        adapter.setOnPlusClickListener(new CartAdapter.onPlusClickListener() {
            @Override
            public void onItemClick(Baginfo info) {
                mhelper.AddBagInfo(info.name,phonenum);
                updateCartList();

            }
        });

        adapter.setOnMinusClickListener(new CartAdapter.onMinusClickListener() {
            @Override
            public void onItemClick(Baginfo info) {
                mhelper.MinusBagInfo(info.name,phonenum);
                updateCartList();
            }
        });


    }

    private void updateCartList() {
        mlist = mhelper.queryForAll(phonenum); // 重新从数据库获取最新的数据列表
        adapter.updateCartList(mlist); // 更新适配器的数据列表
        if (mlist.size() > 0) {
            txt_cart_total.setVisibility(View.VISIBLE);
            txt_cart_empty.setVisibility(View.GONE);
            btn_cart_apply.setVisibility(View.VISIBLE);
        }
        else{
            txt_cart_total.setVisibility(View.GONE);
            txt_cart_empty.setVisibility(View.VISIBLE);
            btn_cart_apply.setVisibility(View.GONE);
        }

        txt_cart_total.setText("总价："+mhelper.getTotalPrice(phonenum)+"元"); // 更新总价
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        orderDBhelper.closeLink();
        mhelper.closeLink();
    }
}