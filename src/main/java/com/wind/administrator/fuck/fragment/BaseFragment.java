package com.wind.administrator.fuck.fragment;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.wind.administrator.fuck.controller.BaseController;
import com.wind.administrator.fuck.listener.IModelChangeListener;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public abstract class BaseFragment extends Fragment implements IModelChangeListener {

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

    /**
     * 抽取吐丝
     * @param msg
     */
    protected void showTip(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void initController() {
    }
}
