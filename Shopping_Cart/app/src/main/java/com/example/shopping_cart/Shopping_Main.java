package com.example.shopping_cart;

import static java.security.AccessController.getContext;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopping_cart.entity.aaa;
import com.example.shopping_cart.fragments.CartFragment;
import com.example.shopping_cart.fragments.MainFragment;
import com.example.shopping_cart.fragments.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Shopping_Main extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private MainFragment mMainFragment;
    private CartFragment mCartFragment;
    private MineFragment mMineFragment;
    private SharedPreferences msharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_main);
        BottomNavigationView bnview=findViewById(R.id.bn_main);
        msharedPreferences = getSharedPreferences("user_name",MODE_PRIVATE);

        if(msharedPreferences.getString(aaa.phonenum,"").equals("")){
            MineFragment.nameInputDialog(msharedPreferences,this);
        }

        bnview.setOnNavigationItemSelectedListener(this);
        selectedFragment(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_home){
            selectedFragment(0);
        }else if(item.getItemId()==R.id.menu_cart){
            selectedFragment(1);
        }else if(item.getItemId()==R.id.menu_mine){
            selectedFragment(2);
        }


        return true;
    }

    private void selectedFragment(int postion){//先隐藏全部，再打开选择的视图
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);

        if(postion==0){
            if(mMainFragment==null){
                mMainFragment=new MainFragment();
                fragmentTransaction.add(R.id.fl_main,mMainFragment);
            }else{
                fragmentTransaction.show(mMainFragment);
            }
        }

        if(postion==1){
            mCartFragment=new CartFragment();
            fragmentTransaction.add(R.id.fl_main,mCartFragment);
        }

        if(postion==2){
            if(mMineFragment==null){
                mMineFragment=new MineFragment();
                fragmentTransaction.add(R.id.fl_main,mMineFragment);
            }else{
                fragmentTransaction.show(mMineFragment);
            }
        }

        fragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction){//隐藏全部视图
        if(mMainFragment!=null){
            fragmentTransaction.hide(mMainFragment);
        }
        if(mCartFragment!=null){
            fragmentTransaction.hide(mCartFragment);
        }
        if(mMineFragment!=null){
            fragmentTransaction.hide(mMineFragment);
        }
    }


}