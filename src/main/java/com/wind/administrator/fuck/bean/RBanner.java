package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/5/20 0020.
 * 广告对象
 */

public class RBanner {
    private long id;
    private int type;
    private String adUrl;
    private String wedUrl;
    private int adKing;

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getWedUrl() {
        return wedUrl;
    }

    public void setWedUrl(String wedUrl) {
        this.wedUrl = wedUrl;
    }

    public int getAdKing() {
        return adKing;
    }

    public void setAdKing(int adKing) {
        this.adKing = adKing;
    }
}
