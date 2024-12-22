package com.example.shopping_cart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.shopping_cart.DBhelpers.LoginDBhelper;
import com.example.shopping_cart.DaosAndDatabases.LoginDao;
import com.example.shopping_cart.DaosAndDatabases.LoginDatabase;
import com.example.shopping_cart.entity.Personinfo;
import com.example.shopping_cart.entity.RoomPersoninfo;
import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.utils.ToastUtil;
import com.example.shopping_cart.utils.ViewUtils;

public class Register extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText et_register_phonenum;
    private EditText et_register_password;
    private EditText et_register_email;
    //private LoginDBhelper dBhelper;
    private EditText et_register_password_confirm;
    private LoginDatabase loginDatabase;
    private LoginDao loginDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        et_register_phonenum = findViewById(R.id.et_register_phonenum);
        et_register_password = findViewById(R.id.et_register_password);
        et_register_email = findViewById(R.id.et_register_email);
        et_register_password_confirm = findViewById(R.id.et_register_password_confirm);
        Button btn_register_register=findViewById(R.id.btn_register_register);
        //dBhelper = LoginDBhelper.getInstance(this);
        ImageView img_register_return=findViewById(R.id.img_register_return);
        ImageView img_register_see1=findViewById(R.id.img_register_see1);
        ImageView img_register_see2=findViewById(R.id.img_register_see2);

        loginDatabase = Room.databaseBuilder(this, LoginDatabase.class,"login.db").allowMainThreadQueries()
                .addMigrations()
                .build();
        loginDao = loginDatabase.loginDao();

        btn_register_register.setOnClickListener(this);
        img_register_return.setOnClickListener(this);
        et_register_phonenum.setOnFocusChangeListener(this);
        et_register_phonenum.addTextChangedListener(new Register.HideTextWatcher(et_register_phonenum,11));
        img_register_see1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    et_register_password.setInputType(129);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN){
                    et_register_password.setInputType(128);
                }
                return true;
            }
        });
        img_register_see2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    et_register_password_confirm.setInputType(129);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN){
                    et_register_password_confirm.setInputType(128);
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_register_register){
            String phone=et_register_phonenum.getText().toString();
            String password=et_register_password.getText().toString();
            String password_confirm=et_register_password_confirm.getText().toString();
            String email=et_register_email.getText().toString();

            if(loginDao.IfPhoneRepeat(phone)){
                ToastUtil.show(this,"此手机号已被注册");
            }else{
                if(phone.length()<11){
                    ToastUtil.show(this,"你的手机号长度不符合要求");
                }else if(password.length()==0||email.length()==0){
                    ToastUtil.show(this,"密码或邮箱不能为空");
                }else if(!password.equals(password_confirm)){
                    ToastUtil.show(this,"两次密码不匹配");
                }else{//所有输入均符合要求时，可以为其注册账号
                    //Personinfo info=new Personinfo(phone,password,email);
                    //dBhelper.insertPerson(info);
                    loginDao.insertOne(new RoomPersoninfo(phone,password,email));

                    //注册成功后，自动填写手机号，但不填密码
//                    SharedPreferences msharedPreferences = getSharedPreferences("after_registerOrfind",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=msharedPreferences.edit();
//                    editor.putBoolean("is_phonenum",true);
//                    editor.putString("phonenum",phone);
//                    editor.commit();
                    aaa.phonenum=phone;
                    loginDao.delete_remember();
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("恭喜你注册成功，请返回重新登陆");
                    builder.setPositiveButton("好的",(dialog,which)->{
                        finish();
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }


            }
        }

        if(v.getId()==R.id.img_register_return){
            finish();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus&&v.getId()==R.id.et_login_phonenum){
            String phone=et_register_phonenum.getText().toString();
            if(TextUtils.isEmpty(phone)||phone.length()!=11){
                //et_tele.requestFocus();
                ToastUtil.show(this,"手机号码长度不符合要求");
            }
        }
    }


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
                ViewUtils.hideOneInputMethod(Register.this,editText);
            }
        }
    }
}