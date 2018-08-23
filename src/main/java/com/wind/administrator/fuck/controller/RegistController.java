package com.wind.administrator.fuck.controller;

import android.content.Context;
import android.net.Network;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class RegistController extends BaseController {

    public RegistController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        if (action == IDivMessage.REGIST_ACTION) {
            RResult registBean = regist((String) values[0], (String) values[1]);
            if(mListener!=null){
                mListener.onModelChanged(action,registBean);
            }
            
        }
    }

    private RResult regist(String name, String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", name);
        params.put("pwd", pwd);
        String jsonStr = NetworkUtil.doPost(NetworkConstant.REGIST_URL, params);
        return JSON.parseObject(jsonStr, RResult.class);
    }
}
