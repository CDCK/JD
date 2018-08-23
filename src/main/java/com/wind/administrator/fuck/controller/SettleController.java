package com.wind.administrator.fuck.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.AddOrderParams;
import com.wind.administrator.fuck.bean.RAddAddressParameter;
import com.wind.administrator.fuck.bean.RArea;
import com.wind.administrator.fuck.bean.RReceiver;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class SettleController extends BaseController {

    public SettleController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action) {
            case IDivMessage.DEFAULT_RECEIVER_ACTION:
                mListener.onModelChanged(action, loadReceiver((long) values[0], (boolean) values[1]));
                break;
            case IDivMessage.RECEIVER_LIST_ACTION:
                mListener.onModelChanged(action, loadReceiver((long) values[0], (boolean) values[1]));
                break;
            case IDivMessage.RECEIVER_DELE_ACTION:
                mListener.onModelChanged(action, deletereceiver((long) values[0], (long) values[1]));
                break;
            case IDivMessage.PROVINCE_ACTION://省份
                mListener.onModelChanged(action, loadProvince());
                break;
            case IDivMessage.CITY_ACTION://城市
                mListener.onModelChanged(action, loadCity((String) values[0]));
                break;
            case IDivMessage.DIST_ACTION://区域
                mListener.onModelChanged(action, loadDist((String) values[0]));
                break;
            case IDivMessage.ADD_RECEIVER_ACTION:
                mListener.onModelChanged(action, loadAddPrceiver((RAddAddressParameter) values[0]));
                break;
            case IDivMessage.ADD_ORDER_ACTION:
                mListener.onModelChanged(action, AddOrder((AddOrderParams) values[0]));
                break;
        }
    }

    /**
     * 添加订单
     *
     * @param addOrderParams
     */
    private RResult AddOrder(AddOrderParams addOrderParams) {

        HashMap<String, String> params = new HashMap<>();
        //要传的参数就是一个JSon语句 addOrderParams
        params.put("detail", JSON.toJSONString(addOrderParams));
        String jsonStr = NetworkUtil.doPost(NetworkConstant.ADD_ORDER_URL, params);
        Log.i("mylog", "AddOrder: 添加订单获取的JSon>>>>>>>>>>>>>>>>>>>"+jsonStr);
        // TODO: 2017/6/17 0017
        return JSON.parseObject(jsonStr,RResult.class);
    }

    /**
     * 添加收货人地址信息
     *
     * @param bean
     */
    private RResult loadAddPrceiver(RAddAddressParameter bean) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", bean.userId + "");
        params.put("name", bean.name);
        params.put("phone", bean.phone);
        params.put("provinceCode", bean.provinceCode);
        params.put("cityCode", bean.cityCode);
        params.put("distCode", bean.distCode);
        params.put("addressDetails", bean.addressDetails);
        params.put("isDefault", bean.isDefault + "");
        String jsonStr = NetworkUtil.doPost(NetworkConstant.ADD_RECEIER_URL, params);
        Log.i("mylog", "loadAddPrceiver: jsonStr-------------------->>>>" + jsonStr);
        return JSON.parseObject(jsonStr, RResult.class);
    }

    /**
     * 返回地区列表
     *
     * @return
     */
    private Object loadDist(String fcode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("fcode", fcode + "");
        String jsonStr = NetworkUtil.doGet(NetworkConstant.AREA_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), RArea.class);
        }
        return new ArrayList<>();
    }

    /**
     * 返回的是市级列表
     */
    private List<RArea> loadCity(String fcode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("fcode", fcode + "");
        String jsonStr = NetworkUtil.doGet(NetworkConstant.CITY_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), RArea.class);
        }
        return new ArrayList<>();
    }

    /**
     * 返回的是省级列表
     */
    private List<RArea> loadProvince() {
        String jsonStr = NetworkUtil.doGet(NetworkConstant.PROVINCE_URL, null);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), RArea.class);
        }
        return new ArrayList<>();
    }

    private RResult deletereceiver(long userId, long receiverId) {
        HashMap<String, String> params = new HashMap<>();
        Log.i("mylog", "deletereceiver: ------------==========" + userId + "<<<<<<<<<<<<<<<<<<<" + receiverId);
        params.put("userId", userId + "");
        params.put("id", receiverId + "");
        String json = NetworkUtil.doPost(NetworkConstant.DELADDRESS_URL, params);
        return JSON.parseObject(json, RResult.class);
    }

    /**
     * 获取收货人地址，既可以获取默认的收货地址，也可以获取收货人的地址列表
     * 如果获取的是默认收货人地址，如果队列里有数据，则数据的长度为1
     */
    private List<RReceiver> loadReceiver(long userId, boolean isDefault) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        if (isDefault) {
            params.put("isDefault", isDefault + "");
        }
        String json = NetworkUtil.doPost(NetworkConstant.RECEIVE_ADDRESS_URL, params);
        RResult resultBean = JSON.parseObject(json, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), RReceiver.class);
        }
        return new ArrayList<>();
    }
}
