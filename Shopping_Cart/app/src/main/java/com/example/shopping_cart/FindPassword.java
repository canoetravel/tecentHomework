package com.example.shopping_cart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.shopping_cart.DBhelpers.LoginDBhelper;
import com.example.shopping_cart.DaosAndDatabases.LoginDao;
import com.example.shopping_cart.DaosAndDatabases.LoginDatabase;
import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.utils.ToastUtil;
import com.example.shopping_cart.utils.ViewUtils;

public class FindPassword extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private LinearLayout ll_find;
    private String phone;
    private EditText et_find_phonenum;
    private EditText et_find_email;
    //private LoginDBhelper dBhelper;
    private boolean is_correct_phonenum;
    private String email;
    private LoginDatabase loginDatabase;
    private LoginDao loginDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_password);
        et_find_phonenum = findViewById(R.id.et_find_phonenum);
        et_find_email = findViewById(R.id.et_find_email);
        Button btn_find_find=findViewById(R.id.btn_find_find);
        ll_find = findViewById(R.id.ll_find);
        //dBhelper = LoginDBhelper.getInstance(this);
//        dBhelper.openReadLink();
//        dBhelper.openWriteLink();
        ImageView img_find_return=findViewById(R.id.img_find_return);

        loginDatabase = Room.databaseBuilder(this, LoginDatabase.class,"login.db").allowMainThreadQueries()
                .addMigrations()
                .build();
        loginDao = loginDatabase.loginDao();

        btn_find_find.setOnClickListener(this);
        et_find_phonenum.setOnFocusChangeListener(this);
        et_find_phonenum.addTextChangedListener(new FindPassword.HideTextWatcher(et_find_phonenum,11));
        img_find_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_find_find){
            if(ll_find.getVisibility()==View.GONE){

                phone=et_find_phonenum.getText().toString();
                if(!loginDao.IfPhoneRepeat(phone)){//没有这个用户
                }else{//有这个用户
                    ll_find.setVisibility(View.VISIBLE);
                }
            }else{
                email=et_find_email.getText().toString();
                //String password=dBhelper.IfEmailCorrect(phone,email);
                String password=loginDao.IfEmailCorrect(phone,email);
                if(password!=null){
//                    SharedPreferences msharedPreferences = getSharedPreferences("after_registerOrfind",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=msharedPreferences.edit();
//                    editor.putBoolean("is_phonenum",true);
//                    editor.putString("phonenum",phone);
//                    editor.commit();
                    aaa.phonenum=phone;
                    loginDao.delete_remember();
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("您的密码是："+password);
                    builder.setPositiveButton("知道了",(dialog,which)->{
                        finish();
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }else{
                    ToastUtil.show(this,"邮箱验证失败");
                }
            }
        }

        if(v.getId()==R.id.img_find_return){
            finish();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus&&v.getId()==R.id.et_find_phonenum){
            String phone=et_find_phonenum.getText().toString();
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
                ViewUtils.hideOneInputMethod(FindPassword.this,editText);
            }
        }
    }
}