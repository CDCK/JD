package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wind.administrator.fuck.bean.RBanner;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.AsyncImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class HomePagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> mItems = new ArrayList<>();
    private final Context mContext;
    private List<RBanner> mDatas;

    public HomePagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 将一项试图添加到ViewPager中
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mItems.get(position);
        container.addView(imageView);
        return imageView;
    }

    /**
     * 将一项试图从ViewPager中移除
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView iv = mItems.get(position);
        container.removeView(iv);

    }

    public void setDatas(List<RBanner> datas) {
        mItems.clear();
        for (int i = 0; i < datas.size(); i++) {
            //取出图片地址
            String url = NetworkConstant.BASE_URL + datas.get(i).getAdUrl();
            //创建一个图片控件并设置宽高
            ImageView iv = new ImageView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            //加载图片内容
            AsyncImageLoader.getInstance(mContext).displayImage(url, iv);
            //将设置好的图片添加到容器中
            mItems.add(iv);
        }
    }
}
