package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RArea;
import com.wind.administrator.fuck.bean.RReceiver;
import com.wind.administrator.fuck.listener.IDeleteReceiverListener;

/**
 * Created by Administrator on 2017/5/20 0020.
 * 选择收货地区Adapter
 */

public class AreaAdapter extends JDBaseAdapter<RArea> {


    /**
     * 构造器
     *
     * @param context
     */
    public AreaAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv ;
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1,parent, false);
            tv = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(tv);
        }else {
            tv = (TextView) convertView.getTag();
        }
        tv.setText(mDatas.get(position).getName());
        return convertView;
    }


}
