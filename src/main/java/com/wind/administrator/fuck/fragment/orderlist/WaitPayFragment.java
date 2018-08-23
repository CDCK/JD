package com.wind.administrator.fuck.fragment.orderlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.fragment.BaseFragment;

/**
 * Created by Administrator on 2017/6/20 0020.
 * //待支付
 */

public class WaitPayFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_pay, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
