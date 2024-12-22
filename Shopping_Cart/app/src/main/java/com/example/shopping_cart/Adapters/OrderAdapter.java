package com.example.shopping_cart.Adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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
import com.example.shopping_cart.entity.Orderinfo;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder>{

    List<Orderinfo> mlist=new ArrayList<>();

    public OrderAdapter(List<Orderinfo> list) {//构造函数
        this.mlist = list;
    }

    public void updateOrderList(List<Orderinfo> newlist) {
        mlist = newlist;
        notifyDataSetChanged(); // 通知数据已经改变
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_in_order, null);
        return new
                OrderAdapter.MyHolder(view);
    }


    @SuppressLint({"ResourceAsColor","RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.img_order.setImageResource(mlist.get(position).image);
        holder.tv_order_name.setText(mlist.get(position).name);
        holder.tv_order_time.setText(mlist.get(position).time);
        holder.tv_order_num.setText(mlist.get(position).num+"件");
        holder.tv_order_totalmoney.setText("共"+mlist.get(position).total+"元");
        if(mlist.get(position).status==2){
            holder.tv_order_complete.setText("已完成");
            holder.tv_order_complete.setTextColor(R.color.font_color);
            holder.btn_order_is_received.setVisibility(View.INVISIBLE);
        }else{
            holder.tv_order_complete.setText("待收货");
            holder.tv_order_complete.setTextColor(R.color.red);
        }
        holder.btn_order_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClickListener.onItemClick(mlist.get(position));
            }
        });
        holder.btn_order_is_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClickListener.onItemClick(mlist.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class  MyHolder extends RecyclerView.ViewHolder{

        ImageView img_order;
        final TextView tv_order_name;
        TextView tv_order_time;
        TextView tv_order_num;
        TextView tv_order_totalmoney;
        TextView tv_order_complete;
        Button btn_order_is_received;
        Button btn_order_delete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img_order = itemView.findViewById(R.id.img_order);
            tv_order_name = itemView.findViewById(R.id.tv_order_name);
            tv_order_time = itemView.findViewById(R.id.tv_order_time);
            tv_order_num = itemView.findViewById(R.id.tv_order_num);
            tv_order_totalmoney = itemView.findViewById(R.id.tv_order_totalmoney);
            tv_order_complete = itemView.findViewById(R.id.tv_order_complete);
            btn_order_is_received = itemView.findViewById(R.id.btn_order_is_received);
            btn_order_delete = itemView.findViewById(R.id.btn_order_delete);
        }
    }


    public void setConfirmClickListener(onConfirmClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

    public void setDeleteClickListener(onDeleteClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }

    private OrderAdapter.onDeleteClickListener deleteClickListener;
    private OrderAdapter.onConfirmClickListener confirmClickListener;

    public interface onConfirmClickListener{
        void onItemClick(Orderinfo info);
    }

    public interface onDeleteClickListener{
        void onItemClick(Orderinfo info);
    }
}
