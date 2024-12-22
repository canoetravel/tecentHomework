package com.example.shopping_cart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopping_cart.Adapters.Main_LeftAdapter;
import com.example.shopping_cart.Adapters.Main_RightAdapter;
import com.example.shopping_cart.utils.DataUtil;
import com.example.shopping_cart.ProductDetails;
import com.example.shopping_cart.R;
import com.example.shopping_cart.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    private View rootView;
    private RecyclerView rv_main_left;
    private Main_LeftAdapter adapter;
    private List<String> name_list;
    private RecyclerView rv_main_right;
    private Main_RightAdapter radapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_main, container, false);
        rv_main_left = rootView.findViewById(R.id.rv_main_left);
        rv_main_right = rootView.findViewById(R.id.rv_main_right);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name_list=new ArrayList<>();
        name_list.add("手机");
        name_list.add("鲜花");
        name_list.add("服装");
        adapter=new Main_LeftAdapter(name_list);
        radapter=new Main_RightAdapter();
        rv_main_left.setAdapter(adapter);
        rv_main_right.setAdapter(radapter);
        radapter.setListData(DataUtil.getList(0));

        adapter.setMleftListOnClickListener(new Main_LeftAdapter.LeftListOnClickListener() {
            @Override
            public void onItemClick(int postion) {
                //ToastUtil.show(getActivity(),"you choose "+postion);
               adapter.setCurrentpostion(postion);

               radapter.setListData(DataUtil.getList(postion));
            }
        });

        radapter.setmItemClickListner(new Main_RightAdapter.onItemClickListener() {
            @Override
            public void onItemClick(ProductInfo info, int position) {
                Intent intent =new Intent(getActivity(), ProductDetails.class);
                intent.putExtra("productInfo",info);
                startActivity(intent);
            }
        });


    }
}




















































