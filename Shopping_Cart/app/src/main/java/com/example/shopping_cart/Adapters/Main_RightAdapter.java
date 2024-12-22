package com.example.shopping_cart.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class Main_RightAdapter extends RecyclerView.Adapter<Main_RightAdapter.MyHolder> {
    private List<ProductInfo> mlist=new ArrayList<>();

    public void setListData(List<ProductInfo> list){
        this.mlist=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_list_item, null);
        return new MyHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder,  int position) {

        ProductInfo info=mlist.get(position);

        holder.image.setImageResource(info.getImage());
        holder.name.setText(info.getName());
        holder.message.setText(info.getMessage());
        holder.price.setText(info.getPrice()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListner.onItemClick(info,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView message;
        TextView price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img_product_image);
            name=itemView.findViewById(R.id.txt_product_name);
            message=itemView.findViewById(R.id.txt_product_message);
            price=itemView.findViewById(R.id.txt_product_price);

        }
    }


    public void setmItemClickListner(onItemClickListener mItemClickListner) {
        this.mItemClickListner = mItemClickListner;
    }


    private onItemClickListener mItemClickListner;

    public interface onItemClickListener{
        void onItemClick(ProductInfo info,int position);
    }
}




















































