package com.example.shopping_cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shopping_cart.DBhelpers.LoginDBhelper;
import com.example.shopping_cart.DaosAndDatabases.LoginDao;
import com.example.shopping_cart.DaosAndDatabases.LoginDatabase;
import com.example.shopping_cart.entity.Personinfo;
import com.example.shopping_cart.entity.RememberPersoninfo;
import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.utils.ToastUtil;
import com.example.shopping_cart.utils.ViewUtils;

public class Login extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{

    private EditText et_login_phonenum;
    private EditText et_login_password;
    private CheckBox ck_login_remember;
    private LoginDatabase loginDatabase;
    private LoginDao loginDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ImageView img_login_return=findViewById(R.id.img_login_return);
        et_login_phonenum = findViewById(R.id.et_login_phonenum);
        et_login_password = findViewById(R.id.et_login_password);
        //Room框架实例------------------------------------------------------------
        loginDatabase = Room.databaseBuilder(this,LoginDatabase.class,"login.db").allowMainThreadQueries()
                .addMigrations()
                .build();
        loginDao = loginDatabase.loginDao();

        Button btn_login_login=findViewById(R.id.btn_login_login);
        TextView text_login_find=findViewById(R.id.text_login_find);
        TextView text_login_register=findViewById(R.id.text_login_register);
        ck_login_remember = findViewById(R.id.ck_login_remember);
        //msharedPreferences = getSharedPreferences("password_remember",MODE_PRIVATE);
//        if(msharedPreferences.getBoolean("is_remember",false)){
//            et_login_phonenum.setText(msharedPreferences.getString("remember_phonenum",""));
//            et_login_password.setText(msharedPreferences.getString("remember_password",""));
//            ck_login_remember.setChecked(true);
//        }


//        if(dBhelper.query_remember()!=null){
//            Personinfo info=dBhelper.query_remember();
//            et_login_phonenum.setText(info.phone);
//            et_login_password.setText(info.password);
//            ck_login_remember.setChecked(true);
//        }
        ImageView img_login_see=findViewById(R.id.img_login_see);


        et_login_phonenum.setOnFocusChangeListener(this);
        et_login_phonenum.addTextChangedListener(new HideTextWatcher(et_login_phonenum,11));
        img_login_return.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
        text_login_find.setOnClickListener(this);
        text_login_register.setOnClickListener(this);
        //dBhelper.deletePhonenum(new String[]{""});

        //img_login_see.setOnClickListener(this);
        img_login_see.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    et_login_password.setInputType(129);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN){
                    et_login_password.setInputType(128);
                }
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
//        dBhelper = LoginDBhelper.getInstance(this);
//        dBhelper.openReadLink();
//        dBhelper.openWriteLink();
//        if(dBhelper.query_remember()!=null){
//            Personinfo info=dBhelper.query_remember();
//            et_login_phonenum.setText(info.phone);
//            et_login_password.setText(info.password);
//            ck_login_remember.setChecked(true);
//        }else{
//            et_login_phonenum.setText(aaa.phonenum);
//            ck_login_remember.setChecked(false);
//        }
        RememberPersoninfo info = loginDao.query_remember();
        if(info!=null){
            et_login_phonenum.setText(info.phone);
            et_login_password.setText(info.password);
            ck_login_remember.setChecked(true);
        }else{
            et_login_phonenum.setText("");
            et_login_password.setText("");
            ck_login_remember.setChecked(false);
        }
        if(aaa.phonenum!=null){
            et_login_phonenum.setText(aaa.phonenum);
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.img_login_return){//返回的点击事件监听
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("您真的要退出吗？");
            builder.setPositiveButton("是的",(dialog,which)->{
                finish();
            });
            builder.setNegativeButton("再考虑一下",(dialog,which)->{

            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if(v.getId()==R.id.btn_login_login){//登录按钮的点击事件监听
            String phone=et_login_phonenum.getText().toString();
            String password=et_login_password.getText().toString();
            if(loginDao.IfPasswordCorrect(phone,password)) {
                aaa.phonenum=phone;
                aaa.password=aaa.password;
                //SharedPreferences.Editor editor= msharedPreferences.edit();
                if(ck_login_remember.isChecked()){//勾选了记住密码
//                    editor.putBoolean("is_remember",true);
//                    editor.putString("remember_phonenum",phone);
//                    editor.putString("remember_password",password);
                    if(loginDao.query_remember()!=null){
                        loginDao.update_remember(phone,password);
                    }else{
                        RememberPersoninfo info=new RememberPersoninfo();
                        info.phone=phone;
                        info.password=password;
                        loginDao.insert_remember(info);
                    }

                    //dBhelper.update_remember(phone,password,ck_login_remember.isChecked());
                }else{//未勾选记住密码
//                    editor.putBoolean("is_remember",false);
//                    editor.putString("remember_phonenum","");
//                    editor.putString("remember_password","");
                    loginDao.delete_remember();//未选择记住，则清空信息
                }
                //editor.apply();

                Intent intent=new Intent(this,Shopping_Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                ToastUtil.show(this,"用户名或密码错误，请重试");
            }
        }

        if(v.getId()==R.id.text_login_find){//点击找回密码
            Intent intent=new Intent(this,FindPassword.class);
            startActivity(intent);
        }

        if(v.getId()==R.id.text_login_register){//点击注册新用户
            Intent intent=new Intent(this,Register.class);
            startActivity(intent);
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus&&v.getId()==R.id.et_login_phonenum){
            String phone=et_login_phonenum.getText().toString();
            if(TextUtils.isEmpty(phone)||phone.length()!=11){
                //et_tele.requestFocus();
                ToastUtil.show(this,"手机号码长度不符合要求");
            }
        }
    }//关注手机长度符不符合要求

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if(isChecked){
//            SharedPreferences.Editor editor= msharedPreferences.edit();
//            editor.putBoolean("is_remember",true);
//            editor.putString("phonenum",et_login_phonenum.getText().toString());
//            editor.putString("password",et_login_password.getText().toString());
//            editor.commit();
//        }else{
//            SharedPreferences.Editor editor= msharedPreferences.edit();
//            editor.putBoolean("is_remember",false);
//            editor.commit();
//        }
//    }


    private class HideTextWatcher implements TextWatcher {
        EditText editText;
        int length;
        public HideTextWatcher(EditText editText, int length) {
            this.editText=editText;
            this.length=length;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().length()==length){
                ViewUtils.hideOneInputMethod(Login.this,editText);
            }
        }
    }//手机号达到长度后自动关闭键盘

//    @Override
//    protected void onStart() {
//        super.onStart();
//        //如果刚刚注册或找回密码，则显示手机号
//        SharedPreferences msharedPreferences = getSharedPreferences("after_registerOrfind",MODE_PRIVATE);
//        if(msharedPreferences.getBoolean("is_phonenum",false)){
//            SharedPreferences.Editor editor=msharedPreferences.edit();
//            editor.putBoolean("is_phonenum",false);
//            editor.commit();
//            et_login_phonenum.setText(msharedPreferences.getString("phonenum",""));
//            et_login_password.setText("");
//            ck_login_remember.setChecked(false);
//        }
//
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d("liu", "onStop: ");
        //dBhelper.closeLink();
    }
}













































