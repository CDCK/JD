package com.wind.administrator.fuck.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.fragment.BaseFragment;
import com.wind.administrator.fuck.fragment.CategoryFragment;
import com.wind.administrator.fuck.fragment.HomeFragment;
import com.wind.administrator.fuck.fragment.MyJdFragment;
import com.wind.administrator.fuck.fragment.ShopcarFragment;
import com.wind.administrator.fuck.listener.IBottomBarItemClickListener;
import com.wind.administrator.fuck.ui.BottomBar;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements IBottomBarItemClickListener{
    private ArrayList<BaseFragment> mFragments;
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragments();
        changeFragment(mFragments.get(0));//HomeFragment
    }

    private void initViews() {
        mBottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        mBottomBar.setIBottomBarItemClickListener(this);
    }

    private void initFragments(){
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(new ShopcarFragment());
        mFragments.add(new MyJdFragment());
    }
    /**
     * 点击底部栏切换Fragment
     * @param fragment
     */
    private void changeFragment(BaseFragment fragment){
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.replace(R.id.top_bar,fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 底部栏点击的item的方法回调
     * @param viewId
     */
    @Override
    public void onItemClick(int viewId) {
        switch (viewId) {
            case R.id.frag_main_ll:
                changeFragment(mFragments.get(0));
                break;
            case R.id.frag_category_ll:
                changeFragment(mFragments.get(1));
                break;
            case R.id.frag_shopcar_ll:
                changeFragment(mFragments.get(2));
                break;
            case R.id.frag_mine_ll:
                changeFragment(mFragments.get(3));
                break;
        }
    }
}
