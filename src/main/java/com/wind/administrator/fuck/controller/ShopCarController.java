package com.wind.administrator.fuck.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.RShopCar;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/29 0029.
 */

public class ShopCarController extends BaseController {

    public ShopCarController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action){
            case IDivMessage.SHOP_CAR_ACTION:
                mListener.onModelChanged(action,loadShopCars((Long) values[0]));
                break;
            case IDivMessage.DELETE_SHOPCAR_ACTION:
                mListener.onModelChanged(action,loadDeleteShopCar((Long) values[0],(Long) values[1]));
                break;
        }
    }

    /**
     *
     * @param userId
     * @param shopId 商品Id
     * @return
     */
    private RResult loadDeleteShopCar(Long userId,Long shopId){
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",userId+"");
        params.put("id",shopId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConstant.DEL_SHOPCAR_URL, params);
        return JSON.parseObject(jsonStr, RResult.class);


    }
    private List<RShopCar> loadShopCars(Long userId){
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConstant.SHOP_CAR_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if(resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(), RShopCar.class);
        }
        return new ArrayList<>();
    }
}
