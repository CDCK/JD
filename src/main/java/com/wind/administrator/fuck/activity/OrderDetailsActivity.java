package com.wind.administrator.fuck.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wind.administrator.fuck.R;

/**
 * 模拟的支付宝
 * account: required (string)
 支付宝账号（15018888888）
 apwd: required (string)
 登陆密码（123456）
 ppwd: required (string)
 支付密码（282828）
 */
public class OrderDetailsActivity extends AppCompatActivity {
    public static final String OID_KEY = "oidkey";
    // TODO: 2017/6/19 0019 货到付款未完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
    }
}
