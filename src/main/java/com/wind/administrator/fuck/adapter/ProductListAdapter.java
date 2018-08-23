package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RProductList;
import com.wind.administrator.fuck.bean.RSecondKill;
import com.wind.administrator.fuck.cons.NetworkConstant;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class ProductListAdapter extends JDBaseAdapter<RProductList> {


     /**
     * 构造器
     *
     * @param context
     */
    public ProductListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.products_lv_item_layout, parent, false);
            holder = new ViewHolder();
            holder.iconIv = (ImageView) convertView.findViewById(R.id.product_iv);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.commentTv = (TextView) convertView.findViewById(R.id.commrate_tv);
            holder.priceTv = (TextView) convertView.findViewById(R.id.price_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RProductList bean = mDatas.get(position);
        mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL + bean.getIconUrl(), holder.iconIv);
        holder.nameTv.setText( bean.getName());
        holder.priceTv.setText("¥ " + bean.getPrice());
        holder.commentTv.setText("1条评价  好评率" + bean.getFavcomRate()+"%");
        return convertView;
    }

    class ViewHolder {
        ImageView iconIv;
        TextView nameTv;
        TextView priceTv;
        TextView commentTv;
    }

    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getId():0 ;
    }
}
