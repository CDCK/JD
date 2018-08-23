package com.wind.administrator.fuck;

import android.app.Application;

import com.wind.administrator.fuck.bean.RLoginUser;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class JDApplication extends Application {
    public RLoginUser mLoginUser;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        LitePal.getDatabase();
    }
    public Long getUserId(){
        return mLoginUser.getId();
    }
}
