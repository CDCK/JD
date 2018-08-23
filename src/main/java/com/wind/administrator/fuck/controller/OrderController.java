package com.wind.administrator.fuck.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.ROrderList;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class OrderController extends BaseController {
    public OrderController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action) {
            case IDivMessage.GET_ORDERS_ACTION:
                mListener.onModelChanged(action, loadOrdersByStatus((Long) values[0], (Integer) values[1]));
                break;
        }
    }

    /**
     * 获取各种状态的订单列表
     * @param userId
     * @param status
     * @return
     */
    private List<ROrderList> loadOrdersByStatus(long userId, int status) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        if (status >= -1) {
            params.put("status", status + "");
        }
        String jsonStr = NetworkUtil.doPost(NetworkConstant.ORDER_LIST_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), ROrderList.class);
        }
        return new ArrayList<>();
    }
}
