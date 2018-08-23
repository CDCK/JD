package com.wind.administrator.fuck.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RBanner;
import com.wind.administrator.fuck.bean.RRecommandProduct;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.RSecondKill;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class HomeController extends BaseController {

    public HomeController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action) {
            case IDivMessage.ADBANNER_ACTION:
                mListener.onModelChanged(action, loadBanners());
                break;
            case IDivMessage.SECOND_KILL_ACTION://秒杀模块
                mListener.onModelChanged(action, loadSecondKills());
                break;
            case IDivMessage.RECOMMAND_PRODUCT_ACTION://猜你喜欢
                mListener.onModelChanged(action, loadRecommandDatas());
                break;
        }
    }

    private List<RRecommandProduct> loadRecommandDatas() {
        Log.i("TAGSD","============="+"进入loadRecommandDatas");
        String jsonStr = NetworkUtil.doGet(NetworkConstant.GET_YOUFAV_URL, null);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);

        if (resultBean.isSuccess()) {
            try {
                JSONObject jsonObject = new JSONObject(resultBean.getResult());
                String rowsJson = jsonObject.getString("rows");
                return JSON.parseArray(rowsJson, RRecommandProduct.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private List<RSecondKill> loadSecondKills() {
        String jsonStr = NetworkUtil.doGet(NetworkConstant.SECOND_KILL_URL, null);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            try {
                JSONObject jsonObject = new JSONObject(resultBean.getResult());
                String rowsJson = jsonObject.getString("rows");
                return JSON.parseArray(rowsJson, RSecondKill.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private List<RBanner> loadBanners() {
        HashMap params = new HashMap();
        params.put("adKing", "1");
        String jsonStr = NetworkUtil.doGet(NetworkConstant.BANNER_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), RBanner.class);
        }
        return new ArrayList<>();
    }
}
