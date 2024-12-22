package com.example.shopping_cart.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.entity.Baginfo;
import com.example.shopping_cart.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder>{

    List<Baginfo> mlist=new ArrayList<>();

    public CartAdapter(List<Baginfo> list) {//构造函数
        this.mlist = list;
    }

    public void updateCartList(List<Baginfo> newCartList) {
        mlist = newCartList;
        notifyDataSetChanged(); // 通知数据已经改变
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_in_bag, null);
        return new MyHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder,  int position) {
        Baginfo info=mlist.get(position);
        holder.bag_img.setImageResource(info.image);
        holder.bag_name.setText(info.name);
        holder.bag_price.setText(info.price+"");
        holder.bag_num.setText(info.num+"");

        holder.itemView.findViewById(R.id.btn_bag_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlusClickListener.onItemClick(mlist.get(position));
            }
        });

        holder.itemView.findViewById(R.id.btn_bag_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMinusClickListener.onItemClick(mlist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class  MyHolder extends RecyclerView.ViewHolder{
        ImageView bag_img;
        TextView bag_name;
        TextView bag_price;
        TextView bag_num;
        ImageButton btn_bag_minus;
        ImageButton btn_bag_plus;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            bag_img=itemView.findViewById(R.id.bag_img);
            bag_name=itemView.findViewById(R.id.bag_name);
            bag_price=itemView.findViewById(R.id.bag_price);
            bag_num=itemView.findViewById(R.id.bag_num);
            btn_bag_minus=itemView.findViewById(R.id.btn_bag_minus);
            btn_bag_plus=itemView.findViewById(R.id.btn_bag_plus);
        }
    }


    public void setOnPlusClickListener(CartAdapter.onPlusClickListener onPlusClickListener) {
        this.onPlusClickListener = onPlusClickListener;
    }

    private onPlusClickListener onPlusClickListener;

    public void setOnMinusClickListener(CartAdapter.onMinusClickListener onMinusClickListener) {
        this.onMinusClickListener = onMinusClickListener;
    }

    private onMinusClickListener onMinusClickListener;

    public interface onPlusClickListener{
        void onItemClick(Baginfo info);
    }

    public interface onMinusClickListener{
        void onItemClick(Baginfo info);
    }

}
