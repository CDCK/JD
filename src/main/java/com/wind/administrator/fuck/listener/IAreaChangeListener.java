package com.wind.administrator.fuck.listener;

import com.wind.administrator.fuck.bean.RArea;

/**
 * Created by Administrator on 2017/6/16 0016.
 * 省市区文本改变的接口回调
 */

public interface IAreaChangeListener {
    void setAreaChange(RArea province,RArea city,RArea area);
}
