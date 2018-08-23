package com.wind.administrator.fuck.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.RSubCategory;
import com.wind.administrator.fuck.bean.RTopCateGory;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/21 0021.
 */

public class CategoryController extends BaseController{


    public CategoryController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action){
            case IDivMessage.TOP_CATEGORY_ACTION:
                mListener.onModelChanged(action, loadTopCateGoryDatas());
                break;
            case IDivMessage.SUB_CATEGORY_ACTION://右边
                mListener.onModelChanged(action, loadSubCategoryDatas((Long) values[0]));
                break;
        }
    }
    private List<RSubCategory> loadSubCategoryDatas(long topcategoryId){
        HashMap<String,String> params = new HashMap<>();
        params.put("parentId",topcategoryId+"");
        String jsonStr = NetworkUtil.doGet(NetworkConstant.CATE_GORY_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if(resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),RSubCategory.class);
        }
        return new ArrayList<>();
    }

    private List<RTopCateGory> loadTopCateGoryDatas() {
        String jsonStr = NetworkUtil.doGet(NetworkConstant.CATE_GORY_URL, null);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if(resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),RTopCateGory.class);
        }
        return new ArrayList<>();
    }

}
