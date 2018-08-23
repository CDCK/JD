package com.wind.administrator.fuck.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 订单列表页下的一个item
 */

public class ROrderList {

    /**
     * items : ["商品图片"]
     * oid : 订单id
     * orderNum : 订单号
     * paid : 是否支付
     * status : 订单状态
     * tn : 订单令牌
     * totalPrice : 订单总金额
     */

    private long oid;
    private String orderNum;
    private boolean paid;
    private int status;
    private String tn;
    private double totalPrice;
    private String items;

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
