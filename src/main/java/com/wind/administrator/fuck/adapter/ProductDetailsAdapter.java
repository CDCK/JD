package com.wind.administrator.fuck.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.wind.administrator.fuck.fragment.BaseFragment;
import com.wind.administrator.fuck.fragment.details.ProductCommentFragment;
import com.wind.administrator.fuck.fragment.details.ProductDetailsFragment;
import com.wind.administrator.fuck.fragment.details.ProductInfoFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    public ProductDetailsAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new ProductInfoFragment());
        mFragments.add(new ProductDetailsFragment());
        mFragments.add(new ProductCommentFragment());

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
