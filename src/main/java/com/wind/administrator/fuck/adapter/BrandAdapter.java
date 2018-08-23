package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RBanner;
import com.wind.administrator.fuck.bean.RBrand;
import com.wind.administrator.fuck.bean.RTopCateGory;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class BrandAdapter extends JDBaseAdapter<RBrand> {
        public int mCurrentTapIndex = -1;//记录点击的item的索引

     /**
     * 构造器
     *
     * @param context
     */
    public BrandAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.brand_gv_item_layout, parent, false);
            tv = (TextView) convertView.findViewById(R.id.brand_tv);
            convertView.setTag(tv);
        } else {
            tv = (TextView) convertView.getTag();
        }
        RBrand bean = mDatas.get(position);
        tv.setText(bean.getName());
        //TOP 3.根据当前的索引（mCurrentTapIndex）来显示新的界面
        tv.setSelected(position == mCurrentTapIndex);
        return convertView;

    }


    @Override
    public RBrand getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getId():0;
    }
}
