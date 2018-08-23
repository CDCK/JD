package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.cons.NetworkConstant;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public abstract class OrderListBaseAdapter<T> extends JDBaseAdapter<T> {
    /**
     * 构造器
     *
     * @param context
     */
    public OrderListBaseAdapter(Context context) {
        super(context);
    }
    protected void initImageContainer(String imgUrls, LinearLayout iamgesContainer) {
        //1.获取图片的数据
        List<String> imageUrls = JSON.parseArray(imgUrls, String.class);
        //2.容器中有几个子控件（imageView）就显示几个 两者取小值
        //计算出数据的长度
        int dataSize = imageUrls.size();
        //获取子控件的数量
        int childCount = iamgesContainer.getChildCount();
        //两者之间取最小
        int min = Math.min(dataSize, childCount);
        //3.让所有的图片控件隐藏
        for (int i = 0; i < childCount; i++) {
            iamgesContainer.getChildAt(i).setVisibility(View.INVISIBLE);
        }
        //4.让需要显示的控件先设置图片源 再显示出来
        for (int i = 0; i < min; i++) {
            ImageView iv = (ImageView) iamgesContainer.getChildAt(i);
            mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL+imageUrls.get(i),iv);
            iv.setVisibility(View.VISIBLE);
        }
        iamgesContainer.setVisibility(dataSize>0?View.VISIBLE:View.GONE);
    }

}
