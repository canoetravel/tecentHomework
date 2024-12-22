package com.example.shopping_cart.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shopping_cart.Login;
import com.example.shopping_cart.Mine_about;
import com.example.shopping_cart.Mine_change_password;
import com.example.shopping_cart.Orders;
import com.example.shopping_cart.R;
import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.utils.PermissionUtil;
import com.example.shopping_cart.utils.ToastUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MineFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 123456;
    private static final int OPEN_GALLERY_REQUEST_CODE = 654321;
    private SharedPreferences msharedPreferences;
    private View rootview;
    String name;
    private ImageButton ib_mine_image;
    private String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Button btn_mine_quit;
    private ImageButton ib_mine_edit;
    private TextView tv_mine_name;
    private RelativeLayout rv_mine_about;
    private RelativeLayout rv_mine_change_password;
    private RelativeLayout rv_mine_orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_mine, container, false);
        ib_mine_edit = rootview.findViewById(R.id.ib_mine_edit);
        tv_mine_name = rootview.findViewById(R.id.tv_mine_name);
        ib_mine_image = rootview.findViewById(R.id.ib_mine_image);
        btn_mine_quit = rootview.findViewById(R.id.btn_mine_quit);
        rv_mine_about = rootview.findViewById(R.id.rv_mine_about);
        rv_mine_change_password = rootview.findViewById(R.id.rv_mine_change_password);
        rv_mine_orders = rootview.findViewById(R.id.rv_mine_orders);

        msharedPreferences = getContext().getSharedPreferences("user_name",MODE_PRIVATE);
        name=msharedPreferences.getString(aaa.phonenum,"");

        ib_mine_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotNewnameInputDialog(msharedPreferences,getContext());
//                name=msharedPreferences.getString("name","");
//                tv_mine_name.setText(name);

//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        name = msharedPreferences.getString("name", "");
//                        tv_mine_name.setText(name);
//                    }
//                });
            }
        });//为昵称设置点击事件监听
        ib_mine_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) open11Gallery();
                else applyPermission();
            }
        });

        btn_mine_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        rv_mine_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Mine_about.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        rv_mine_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Mine_change_password.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        rv_mine_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Orders.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_mine_name.setText(name);

    }

    static public void nameInputDialog(SharedPreferences msharedPreferences, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.input_dialog, null);
        final EditText inputEditText = view.findViewById(R.id.et_hint_inputEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("您是注册的新用户，为自己起一个昵称吧");//标题
        builder.setView(view);//设置视图
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置同意
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取用户输入的文本
                        String inputText = inputEditText.getText().toString();
                        // 处理输入的文本，例如显示或存储
                        SharedPreferences.Editor editor= msharedPreferences.edit();
                        editor.putString(aaa.phonenum,inputText);
                        editor.apply();
                        ToastUtil.show(context,"设置成功，在“我的”界面可以更改昵称哦");
                    }
                });


        builder.show();
    }//初次输入昵称

    public void NotNewnameInputDialog(SharedPreferences msharedPreferences, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.input_dialog, null);
        final EditText inputEditText = view.findViewById(R.id.et_hint_inputEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);//设置视图
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置同意
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取用户输入的文本
                String inputText = inputEditText.getText().toString();
                // 处理输入的文本，例如显示或存储
                SharedPreferences.Editor editor= msharedPreferences.edit();
                editor.putString(aaa.phonenum,inputText);
                editor.apply();
                ToastUtil.show(context,"设置成功");

                name = msharedPreferences.getString(aaa.phonenum, "");
                tv_mine_name.setText(name);

            }
        });


        builder.show();
    }//更改名称

    private void applyPermission() {
        if(PermissionUtil.checkPermission(getActivity(), permissions, PERMISSION_REQUEST_CODE)){
            //Log.d("liu", "1");
            openGallery();
        }

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
    }


    private void open11Gallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Log.d("liu", "1");
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (PermissionUtil.checkGrant(grantResults)) {
                openGallery();
            } else {
                ToastUtil.show(getContext(),"访问相册权限未被授予");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                try {
                    InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ib_mine_image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}