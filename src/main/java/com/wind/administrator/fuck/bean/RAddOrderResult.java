package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/6/17 0017.
 * 添加订单成功 返回的结果
 */

public class RAddOrderResult {


    /**
     * errorType : (0-成功 1-产品没库存了 2-系统失败)
     * buyTime : 2016-03-16 14:37:35
     * freight : 运费
     * totalPrice : 应付总金额
     * oid : 订单id
     * orderNum : 订单号
     * pname : 京东商品
     * payWay : 付款方式（ 0在线支付 1货到付款）
     * tn : 订单令牌
     * allPrice : 商品总价格
     */
    private double allPrice;
    private double freight;
    private double totalPrice;
    private long oid;
    private String orderNum;

    private int errorType;

    private int payWay;
    private String buyTime;
    private String pname;
    private String tn;

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }
}

