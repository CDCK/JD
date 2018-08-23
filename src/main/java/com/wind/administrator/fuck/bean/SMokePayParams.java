package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class SMokePayParams {

    public String account ;
    public String apwd ;
    public String ppwd ;
    public String tn ;
    public long userId ;

    public SMokePayParams(String account, String apwd, String ppwd, String tn, long userId) {
        this.account = account;
        this.apwd = apwd;
        this.ppwd = ppwd;
        this.tn = tn;
        this.userId = userId;
    }
}
