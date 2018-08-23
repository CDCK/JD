package com.wind.administrator.fuck.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wind.administrator.fuck.controller.BaseController;
import com.wind.administrator.fuck.listener.IModelChangeListener;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class BaseActivity extends AppCompatActivity implements IModelChangeListener {

    protected BaseController mController;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleUI(msg);
        }
    };

    protected void handleUI(Message msg) {
        //因为有的要实现网络请求，有的子类不需要，所以只需要空实现，让子类去覆写该方法
    }

    @Override
    public void onModelChanged(int action, Object resultBean) {
        mHandler.obtainMessage(action, resultBean).sendToTarget();
    }

    protected void showTip(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void initController() {
    }

    /**
     * 每一个页面都有一个goBack点击事件，可以交给父类
     *
     * @param view
     */
    public void goBack(View view) {
        finish();
    }
}