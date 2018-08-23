package com.wind.administrator.fuck.fragment.orderlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.adapter.AllOrderAdapter;
import com.wind.administrator.fuck.bean.OrderStatus;
import com.wind.administrator.fuck.cons.IDivMessage;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 所有订单
 */

public class AllOrderFragment extends OrderListBaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);
        return view;
    }


    @Override

    protected void requsetOrderListDatas() {
        JDApplication application = (JDApplication) getActivity().getApplication();
        mController.sendAsyncMessage(IDivMessage.GET_ORDERS_ACTION, application.getUserId(), OrderStatus.All_ORDER);
    }

    @Override
    protected void initView() {
        initXListView(R.id.all_order_lv,AllOrderAdapter.class);
    }
}
