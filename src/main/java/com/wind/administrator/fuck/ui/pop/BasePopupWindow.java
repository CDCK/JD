package com.wind.administrator.fuck.ui.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public abstract class BasePopupWindow {
    public Context mContext;
    public PopupWindow mPopupWindow;

    protected abstract void initViews();

    protected abstract void onShow(View anchorView);

    public void onDismiss() {
        if (mPopupWindow.isShowing()) {
            //如果当前mPopupWindow显示出来了
            mPopupWindow.dismiss();
        }
    }
}
