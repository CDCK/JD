package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class OrderStatus {
    public static final int All_ORDER = -2;
    public static final int CANCEL_ORDER = -1;
    public static final int WAIT_PAY_ORDER = 0;
    public static final int WAIT_DEVELIVER_ORDER = 1;
    public static final int WAIT_RECEIVE_ORDER = 2;
    public static final int COMPLETED_ORDER = 3;

    public static String getStatus(int status) {
        switch (status) {
            case All_ORDER:
                return "全部订单：";
            case CANCEL_ORDER:
                return "取消订单：";
            case WAIT_PAY_ORDER:
                return "去支付：";
            case WAIT_DEVELIVER_ORDER:
                return "代发货：";
            case WAIT_RECEIVE_ORDER:
                return "待收货：";
            case COMPLETED_ORDER:
                return "交易完成：";
            default:
                return "";
        }
    }
}
