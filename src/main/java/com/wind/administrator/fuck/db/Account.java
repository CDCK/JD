package com.wind.administrator.fuck.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class Account extends DataSupport {
    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
