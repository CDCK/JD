package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.wind.administrator.fuck.bean.RSecondKill;
import com.wind.administrator.fuck.fragment.ShopcarFragment;
import com.wind.administrator.fuck.util.AsyncImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public abstract class JDBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected final LayoutInflater mInflater;
    protected final AsyncImageLoader mAsyncImageLoader;

    /**
     * 构造器
     *
     * @param context
     */
    public JDBaseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mAsyncImageLoader = AsyncImageLoader.getInstance(context);
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;

    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public Object getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
