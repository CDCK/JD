package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RSecondKill;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.AsyncImageLoader;

import java.net.NetworkInterface;
import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class SecondKillAdapter extends JDBaseAdapter<RSecondKill> {


     /**
     * 构造器
     *
     * @param context
     */
    public SecondKillAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.seckill_item_layout, parent, false);
            holder = new ViewHolder();
            holder.iconIv = (ImageView) convertView.findViewById(R.id.icon_iv);
            holder.priceTv = (TextView) convertView.findViewById(R.id.normalprice_tv);
            holder.nowPriceTv = (TextView) convertView.findViewById(R.id.nowprice_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RSecondKill bean = mDatas.get(position);
        mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL + bean.getIconUrl(), holder.iconIv);
        holder.nowPriceTv.setText("¥ " + bean.getPointPrice());
        holder.priceTv.setText("¥ " + bean.getAllPrice());
        return convertView;
    }

    class ViewHolder {
        ImageView iconIv;
        TextView nowPriceTv;
        TextView priceTv;
    }


}
