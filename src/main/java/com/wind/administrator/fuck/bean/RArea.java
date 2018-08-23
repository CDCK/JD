package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 省市区的都一样
 */

public class RArea {

    /**
     * name : 北京市
     * code : 110000
     */

    private String name; //展示的数据
    private String code;//后台返回的编码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
