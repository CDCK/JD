package com.wind.administrator.fuck.controller;

import android.content.Context;

import com.wind.administrator.fuck.listener.IModelChangeListener;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public abstract class BaseController {
    protected IModelChangeListener mListener;
    protected final WeakReference<Context> mContext;

    public BaseController(Context c) {
        mContext = new WeakReference<Context>(c);
    }

    /**
     * 定义了一个子线程的操作
     *
     * @param values
     */
    public void sendAsyncMessage(final int action, final Object... values) {
        new Thread() {
            @Override
            public void run() {
                handleMessage(action, values);
            }
        }.start();
    }

    /**
     * 定义了一个抽象方法
     *
     * @param values
     */
    protected abstract void handleMessage(final int action, Object[] values);

    public void setIModelChangeListener(IModelChangeListener listener) {
        this.mListener = listener;

    }
}
