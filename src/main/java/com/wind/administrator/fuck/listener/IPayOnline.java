package com.wind.administrator.fuck.listener;

/**
 * Created by Administrator on 2017/6/17 0017.
 * 在线支付点击事件的处理结果
 */

public interface IPayOnline {
    void onSubmit(String tn);

    void onCancel();
}
