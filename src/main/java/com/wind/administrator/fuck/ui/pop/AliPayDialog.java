package com.wind.administrator.fuck.ui.pop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.AliPayActivity;
import com.wind.administrator.fuck.listener.IPayResultListener;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 它的实现跟Activity一样
 */

public class AliPayDialog extends Dialog implements View.OnClickListener {
    private EditText mAccountEt;
    private EditText mPwdEt;
    private EditText mPayPwdEt;
    private Button mCancelBtn;
    private Button mPayBtn;
    private IPayResultListener mListener;

    public AliPayDialog(Context context) {
        super(context);
    }

    //第二个参数需要传入 一个主题资源的ID （样式）
    /*public AliPayDialog(Context context, int themeResId) {

        super(context, themeResId);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alipay_pop_view);
        initView();
    }

    private void initView() {
        mAccountEt = (EditText) findViewById(R.id.account_et);
        mPwdEt = (EditText) findViewById(R.id.pwd_et);
        mPayPwdEt = (EditText) findViewById(R.id.pay_pwd_et);
        mCancelBtn = (Button) findViewById(R.id.cancel_btn);
        mPayBtn = (Button) findViewById(R.id.pay_btn);

        mCancelBtn.setOnClickListener(this);
        mPayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn://取消按钮
                dismiss();
                break;
            case R.id.pay_btn:
                //数据校验
                String account = mAccountEt.getText().toString();
                String pwd = mPwdEt.getText().toString();
                String paypwd = mPayPwdEt.getText().toString();
                if(mListener!=null){
                    mListener.onPayResult(account,pwd,paypwd);
                }
                break;
        }
    }


    public void setListener(IPayResultListener listener) {
        mListener = listener;
    }
}
