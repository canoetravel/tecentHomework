package com.example.shopping_cart;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_cart.DBhelpers.CartDBhelper;
import com.example.shopping_cart.DBhelpers.OrderDBhelper;
import com.example.shopping_cart.entity.Orderinfo;
import com.example.shopping_cart.entity.ProductInfo;
import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.utils.TimeUtil;
import com.example.shopping_cart.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_product_detail_image;
    private TextView txt_product_detail_name;
    private TextView txt_product_detail_message;
    private TextView txt_product_detail_price;

    private ProductInfo info;
    private ImageView img_product_detail_return;
    private Button btn_product_detail_buy;
    private CartDBhelper dBhelper;
    private SharedPreferences msharedPreferences;
    private String phonenum= aaa.phonenum;
    private Button btn_product_detail_buynow;
    private OrderDBhelper orderDBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);

        dBhelper = CartDBhelper.getInstance(this);
        dBhelper.openWriteLink();
        dBhelper.openReadLink();
        info= (ProductInfo) getIntent().getSerializableExtra("productInfo");
        img_product_detail_image = findViewById(R.id.img_product_detail_image);
        txt_product_detail_name = findViewById(R.id.txt_product_detail_name);
        txt_product_detail_message = findViewById(R.id.txt_product_detail_message);
        txt_product_detail_price = findViewById(R.id.txt_product_detail_price);
        img_product_detail_return = findViewById(R.id.img_product_detail_return);
        btn_product_detail_buy = findViewById(R.id.btn_product_detail_buy);
        btn_product_detail_buynow = findViewById(R.id.btn_product_detail_buynow);
        orderDBhelper = OrderDBhelper.getInstance(this);
        orderDBhelper.openWriteLink();
        orderDBhelper.openReadLink();
//        msharedPreferences = getSharedPreferences("password_remember",MODE_PRIVATE);
//        phonenum=msharedPreferences.getString("phonenum","");

        img_product_detail_return.setOnClickListener(this);
        btn_product_detail_buy.setOnClickListener(this);
        btn_product_detail_buynow.setOnClickListener(this);

        //设置数据
        if(info!=null){
            img_product_detail_image.setImageResource(info.getImage());
            txt_product_detail_name.setText(info.getName());
            txt_product_detail_message.setText(info.getMessage());
            txt_product_detail_price.setText(info.getPrice()+"");
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.img_product_detail_return){
            finish();
        }

        if(v.getId()==R.id.btn_product_detail_buy){//加入购物车
            dBhelper.addToCart(info,phonenum);
            ToastUtil.show(this,"已添加"+info.getName()+"进购物车");
        }

        if(v.getId()==R.id.btn_product_detail_buynow){//立即购买
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("您确定要购买吗");
            builder.setPositiveButton("是的",(dialog,which)->{
                Orderinfo orderinfo=new Orderinfo();
                orderinfo.num=1;
                orderinfo.image=info.getImage();
                orderinfo.name=info.getName();
                orderinfo.total=info.getPrice();
                orderinfo.status=1;
                orderinfo.time= TimeUtil.getCurrentTime();
                List<Orderinfo> orderinfos=new ArrayList<>();
                orderinfos.add(orderinfo);
                orderDBhelper.insertOrder(orderinfos,phonenum);
                ToastUtil.show(this,"已购买"+info.getName()+"一件");
            });
            builder.setNegativeButton("再考虑一下", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }
}



































