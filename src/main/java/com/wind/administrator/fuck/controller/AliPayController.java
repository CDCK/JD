package com.wind.administrator.fuck.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.SMokePayParams;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/17 0017.
 */

public class AliPayController extends BaseController {
    public AliPayController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action){
            case IDivMessage.GET_PAYINFO_ACTION:
                mListener.onModelChanged(action,getPayInfo((Long) values[0],(String)values[1]));
                break;
            case IDivMessage.MOKE_PAY_ACTION:
                mListener.onModelChanged(action,mockPay((SMokePayParams) values[0]));
                break;
        }
    }

    /**
     * 模拟支付
     * @param paramsBean
     * @return RResult 失败需要告诉用户原因，成功需要返回 订单id
     */
    private RResult mockPay(SMokePayParams paramsBean){
        HashMap<String, String> params = new HashMap<>();
        params.put("account",paramsBean.account);
        params.put("apwd",paramsBean.apwd);
        params.put("ppwd",paramsBean.ppwd);
        params.put("tn",paramsBean.tn);
        params.put("userId",paramsBean.userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConstant.MOCK_PAY_URL, params);
        return JSON.parseObject(jsonStr, RResult.class);

    }
    private RResult getPayInfo(long userId,String tn){
        HashMap<String, String> params = new HashMap<>();
        params.put("tn",tn);
        params.put("userId",userId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConstant.GET_PAYINFO_URL, params);
        Log.i("mylog", "getPayInfo: jsonStr--------------------------->>>>>"+jsonStr);
        return JSON.parseObject(jsonStr, RResult.class);

    }
}
