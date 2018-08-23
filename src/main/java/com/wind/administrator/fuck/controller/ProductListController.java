package com.wind.administrator.fuck.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RBrand;
import com.wind.administrator.fuck.bean.RProductList;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.SProductListParams;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class ProductListController extends BaseController {

    public ProductListController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action){
            case IDivMessage.BRAND_ACTION:
                loadBrands((Long) values[0]);
                mListener.onModelChanged(action,loadBrands((Long) values[0]));
                break;
            case IDivMessage.PRODUCT_LIST_ACTION:
                mListener.onModelChanged(action,loadProductListDatas((SProductListParams) values[0]));
                break;
        }
    }
    private List<RProductList> loadProductListDatas(SProductListParams params){
        HashMap<String, String> paramsMap = buildProductListSendParams(params);
        String jsonStr = NetworkUtil.doPost(NetworkConstant.PRODUCT_LIST_URL, paramsMap);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if(resultBean.isSuccess()){
            try {
                JSONObject jsonObject = new JSONObject(resultBean.getResult());
                String rows = jsonObject.getString("rows");
                return JSON.parseArray(rows,RProductList.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @NonNull
    private HashMap<String, String> buildProductListSendParams(SProductListParams params) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("categoryId",params.categoryId+"");
        paramsMap.put("filterType",params.filterType+"");
        if(params.sortType!= SProductListParams.SORT_TYPE_DEFAULT){
        paramsMap.put("sortType",params.sortType+"");
        }
        paramsMap.put("deliverChoose",params.deliverChoose+"");
        if(params.brandId != -1){
        paramsMap.put("brandId",params.brandId+"");
        }
        return paramsMap;
    }

    private List<RBrand> loadBrands(long topCategoryId){
        HashMap<String, String> params = new HashMap<>();
        params.put("categoryId",topCategoryId+"");
        String jsonStr = NetworkUtil.doGet(NetworkConstant.BRAND_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if(resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(), RBrand.class);
        }
        return new ArrayList<>();
    }
}
