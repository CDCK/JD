package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class AddOrderProductParams {
    private int buyCount;
    private String type;
    private long pid;

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}
