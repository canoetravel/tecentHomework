package com.example.shopping_cart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.Adapters.OrderAdapter;
import com.example.shopping_cart.DBhelpers.OrderDBhelper;
import com.example.shopping_cart.entity.Baginfo;
import com.example.shopping_cart.entity.Orderinfo;
import com.example.shopping_cart.entity.aaa;

import java.util.List;

public class Orders extends AppCompatActivity implements View.OnClickListener {

    private OrderDBhelper dBhelper;
    private String user_name;
    private List<Orderinfo> list;
    private TextView tv_mine_order_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);
        Button btn_orders_close=findViewById(R.id.btn_orders_close);
        RecyclerView ry_orders=findViewById(R.id.ry_orders);
        tv_mine_order_empty = findViewById(R.id.tv_mine_order_empty);
        dBhelper = OrderDBhelper.getInstance(this);
        dBhelper.openReadLink();
        dBhelper.openWriteLink();
        user_name=aaa.phonenum;

        list = dBhelper.queryForAll(user_name);
        if(list.size()==0){//购物车是空的
            tv_mine_order_empty.setVisibility(View.VISIBLE);
        }else{//购物车中有商品
            tv_mine_order_empty.setVisibility(View.GONE);
        }

        //Log.d("liu", user_name+": "+list.size()+"");
        OrderAdapter adapter=new OrderAdapter(list);
        ry_orders.setAdapter(adapter);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        adapter.setConfirmClickListener(new OrderAdapter.onConfirmClickListener() {
            @Override
            public void onItemClick(Orderinfo info) {
                builder.setTitle("您收到货了吗");
                builder.setPositiveButton("是的",(dialog,which)->{
                    String name=info.name;
                    String time=info.time;
                    dBhelper.ChangeStatus(name,user_name,time);
                    list=dBhelper.queryForAll(user_name);
                    adapter.updateOrderList(list);
                });
                builder.setNegativeButton("还没有",(dialog,which)->{

                });
                AlertDialog dialog=builder.create();
                dialog.show();

            }
        });
        adapter.setDeleteClickListener(new OrderAdapter.onDeleteClickListener() {
            @Override
            public void onItemClick(Orderinfo info) {
                builder.setTitle("删除后不可找回，您确定要删除吗？");
                builder.setPositiveButton("是的",(dialog,which)->{
                    String name=info.name;
                    String time=info.time;
                    dBhelper.deleteinfo(name,user_name,time);
                    list=dBhelper.queryForAll(user_name);
                    adapter.updateOrderList(list);
                });
                builder.setNegativeButton("再考虑一下",(dialog,which)->{

                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        btn_orders_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_orders_close){
            finish();
        }
    }


}