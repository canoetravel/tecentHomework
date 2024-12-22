package com.example.shopping_cart.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;

import java.util.ArrayList;
import java.util.List;

public class Main_LeftAdapter extends RecyclerView.Adapter<Main_LeftAdapter.MyHolder> {

    private List<String> list=new ArrayList<>();
    private int currentpostion=0;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//构建每一个MyHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_list_item, null);
        return new MyHolder(view);
    }

    public Main_LeftAdapter(List<String> list) {//构造函数
        this.list = list;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder,  int position) {//具体实现每一个MyHolder
        String name=list.get(position);
        holder.tv_name.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mleftListOnClickListener!=null){
                    mleftListOnClickListener.onItemClick(position);
                }
            }
        });
        if(currentpostion==position){
            holder.itemView.setBackgroundResource(R.drawable.main_left_selector);
        }else{
            holder.itemView.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class  MyHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.name);
        }
    }

    public LeftListOnClickListener getMleftListOnClickListener() {
        return mleftListOnClickListener;
    }



    public void setMleftListOnClickListener(LeftListOnClickListener mleftListOnClickListener) {
        this.mleftListOnClickListener = mleftListOnClickListener;
    }

    private LeftListOnClickListener mleftListOnClickListener;

    public interface LeftListOnClickListener{
        void onItemClick(int postion);
    }

    public void setCurrentpostion(int position){
        this.currentpostion=position;
        notifyDataSetChanged();
    }

}






























