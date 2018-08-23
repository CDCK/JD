package com.wind.administrator.fuck.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wind.administrator.fuck.R;


public class RatingBar extends LinearLayout {

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * max为 0
     */
    public void setRating(int count) {
        //先让星星重置下，清除星星数量（全部设置成灰色的）
        int childCount = getChildCount();//获取到所有控件的数量(5)
        for (int i = 0; i < childCount; i++) {
            ImageView iv = (ImageView) getChildAt(i);
            iv.setImageResource(R.drawable.start_selected);
        }
//        for (int i = 0; i < count; i++) {
//            ImageView iv = (ImageView) getChildAt(i);
//            iv.setImageResource(R.drawable.start_selected);
//        }
    }

}
