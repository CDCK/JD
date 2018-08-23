package com.wind.administrator.fuck.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/16 0016.
 * 请求参数，不是返回参数
 */

public class AddOrderParams {
    private ArrayList<AddOrderProductParams> products;
    private long addrId;
    private int payWay;
    private long userId;
    //  方便将对象与JSON转换调用
    public AddOrderParams() {
    }

    public AddOrderParams(ArrayList<AddOrderProductParams> products, long addrId, int payWay, long userId) {
        this.products = products;
        this.addrId = addrId;
        this.payWay = payWay;
        this.userId = userId;
    }

    public ArrayList<AddOrderProductParams> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<AddOrderProductParams> products) {
        this.products = products;
    }

    public long getAddrId() {
        return addrId;
    }

    public void setAddrId(long addrId) {
        this.addrId = addrId;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
