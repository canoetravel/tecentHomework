package com.example.shopping_cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.shopping_cart.DBhelpers.LoginDBhelper;
import com.example.shopping_cart.DaosAndDatabases.LoginDao;
import com.example.shopping_cart.DaosAndDatabases.LoginDatabase;
import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.utils.ToastUtil;

public class Mine_change_password extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "liu";
    private SharedPreferences mpreference;
    private EditText et_mine_find_password;
    private EditText et_mine_confirm_password;
    private Button btn_mine_find;
    //private LoginDBhelper dBhelper;
    private String previous_password=null;
    private LoginDatabase loginDatabase;
    private LoginDao loginDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mine_change_password);
        mpreference = getSharedPreferences("password_remember",MODE_PRIVATE);
        et_mine_find_password = findViewById(R.id.et_mine_find_password);
        et_mine_confirm_password = findViewById(R.id.et_mine_confirm_password);
        btn_mine_find = findViewById(R.id.btn_mine_find);
        loginDatabase = Room.databaseBuilder(this, LoginDatabase.class,"login.db").allowMainThreadQueries()
                .addMigrations()
                .build();
        loginDao = loginDatabase.loginDao();

//        dBhelper = LoginDBhelper.getInstance(this);
//        dBhelper.openReadLink();
//        dBhelper.openWriteLink();
        resume();



        btn_mine_find.setOnClickListener(this);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dBhelper.closeLink();
//    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_mine_find){//点击找回密码
            if(et_mine_find_password.getHint().toString().equals("请输入你的密码")){
                //Log.d(TAG, "1");
                String password1=et_mine_find_password.getText().toString();
                String password2=et_mine_confirm_password.getText().toString();

                if(!password1.equals(password2)){
                    ToastUtil.show(this,"两次输入的密码不匹配");
                }else{
                    String phone= aaa.phonenum;
                    //String phone=mpreference.getString("get_phonenum",null);
                    //Log.d(TAG, phone);
                    if(phone!=null){
                        if(!loginDao.IfPasswordCorrect(phone,password1)){
                            ToastUtil.show(this,"密码输入错误");
                        }else{//输入正确
                            et_mine_find_password.setHint("请输入新密码");
                            et_mine_confirm_password.setHint("请确认新密码");
                            et_mine_find_password.setText("");
                            et_mine_confirm_password.setText("");
                            previous_password=password1;
                            ToastUtil.show(this,"请输入新密码");
                        }
                    }

                }
            }else{//输入了确认新密码
                //Log.d(TAG, "2");
                String password1=et_mine_find_password.getText().toString();
                String password2=et_mine_confirm_password.getText().toString();
                if(!password1.equals(password2)){
                    ToastUtil.show(this,"两次输入的密码不匹配");
                }else{
                    if(password1.equals(previous_password)){
                        ToastUtil.show(this,"新密码不能和旧密码相同");
                    }else{
                        String phone=aaa.phonenum;
                        //dBhelper.changePassword(phone,password1);
                        loginDao.updatePasswordByPhone(phone,password1);
                        loginDao.delete_remember();
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.setTitle("修改成功，请重新登陆");
                        builder.setPositiveButton("好的",(dialog,which)->{
                            Intent intent=new Intent(this,Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }


                }

            }

        }
    }

    private void resume(){
        et_mine_find_password.setHint("请输入你的密码");
        et_mine_confirm_password.setHint("确认你的密码");
    }

}