package com.wind.administrator.fuck.controller;

/**
 * Created by Administrator on 2017/6/17 0017.
 */

public class RPayInfo {

    /**
     * oinfo : JD+订单号
     * tn : 订单令牌
     * pname : 京东商品
     * payTime : 购买时间
     * totalPrice : 总金额
     */

    private String oinfo;
    private String tn;
    private String pname;
    private String payTime;
    private double totalPrice;

    public String getOinfo() {
        return oinfo;
    }

    public void setOinfo(String oinfo) {
        this.oinfo = oinfo;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
