package com.wind.administrator.fuck.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class LoginController extends BaseController {


    public LoginController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        login((String) values[0], (String) values[1]);
        switch (action) {
            case IDivMessage.LOGIN_ACTION://登录的常量
                RResult result = login((String) values[0], (String) values[1]);
                if(mListener!=null){
                    mListener.onModelChanged(action,result);
                }
                break;
            case IDivMessage.REGIST_ACTION://注册的常量
//                regist((String) values[0], (String) values[1]);
                break;
        }
    }


    public RResult login(final String name, final String pwd) {

        HashMap<String, String> params = new HashMap<String, String>();
        //将获取到的账号和密码放入到Map集合中
        params.put("username", name);
        params.put("pwd", pwd);
        //调用工具类，获取到一个json数据
        String jsonStr = NetworkUtil.doPost(NetworkConstant.LOGIN_URL, params);
        return JSON.parseObject(jsonStr, RResult.class);

    }


}
