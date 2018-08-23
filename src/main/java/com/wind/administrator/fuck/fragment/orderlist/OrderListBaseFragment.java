package com.wind.administrator.fuck.fragment.orderlist;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import com.wind.administrator.fuck.adapter.OrderListBaseAdapter;
import com.wind.administrator.fuck.bean.ROrderList;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.OrderController;
import com.wind.administrator.fuck.fragment.BaseFragment;
import com.wind.administrator.fuck.ui.xlistview.XListView;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public abstract class OrderListBaseFragment extends BaseFragment implements XListView.IXListViewListener {
    protected XListView mOrdersLv;
    protected OrderListBaseAdapter mAdapter;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDivMessage.GET_ORDERS_ACTION:
                handleLoadOrderListLv((List<ROrderList>) msg.obj);
                break;
        }
    }

    /**
     * 处理加载订单列表
     * @param datas
     */
    private void handleLoadOrderListLv(List<ROrderList> datas) {
        //设置更新的时间,下拉时显示
        mOrdersLv.setRefreshTime(getCurrentTime());
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 加载更新时的时间
     * @return
     */
    protected String getCurrentTime() {
        //下拉更新时会有更新的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initView();
        requsetOrderListDatas();
    }

    @Override
    protected void initController() {
        mController = new OrderController(getActivity());
        mController.setIModelChangeListener(this);
    }

    protected abstract void initView();

    protected abstract void requsetOrderListDatas();


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        requsetOrderListDatas();
        // TODO: 2017/6/21 0021  
        onStop();
    }

    protected void initXListView(int resId, Class clazz) {
        mOrdersLv = (XListView) getActivity().findViewById(resId);
        mOrdersLv.setPullRefreshEnable(true);
        mOrdersLv.setPullLoadEnable(false);
        mOrdersLv.setXListViewListener(this);
//        mAdapter = new AllOrderAdapter(getActivity());
        //拿到子类传递过来的构造器
        try {
            Constructor constructor = clazz.getDeclaredConstructor(Context.class);
            mAdapter = (OrderListBaseAdapter) constructor.newInstance(getActivity());
            mOrdersLv.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
