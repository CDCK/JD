package com.wind.administrator.fuck.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wind.administrator.fuck.fragment.BaseFragment;
import com.wind.administrator.fuck.fragment.details.ProductCommentFragment;
import com.wind.administrator.fuck.fragment.details.ProductDetailsFragment;
import com.wind.administrator.fuck.fragment.details.ProductInfoFragment;
import com.wind.administrator.fuck.fragment.orderlist.AllOrderFragment;
import com.wind.administrator.fuck.fragment.orderlist.CompleteOrderFragment;
import com.wind.administrator.fuck.fragment.orderlist.WaitPayFragment;
import com.wind.administrator.fuck.fragment.orderlist.WaitReceiverFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class OrdersContainerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    public OrdersContainerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new AllOrderFragment());
        mFragments.add(new WaitPayFragment());
        mFragments.add(new WaitReceiverFragment());
        mFragments.add(new CompleteOrderFragment());

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
