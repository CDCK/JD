package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.OrderStatus;
import com.wind.administrator.fuck.bean.ROrderList;
import com.wind.administrator.fuck.cons.NetworkConstant;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class AllOrderAdapter extends OrderListBaseAdapter<ROrderList> {


    /**
     * 构造器
     *
     * @param context
     */
    public AllOrderAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.all_order_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ROrderList bean = mDatas.get(position);
        holder.mOrderNoTv.setText("订单编号:"+bean.getOrderNum());
        String status = OrderStatus.getStatus(bean.getStatus());
        holder.mOrderStateTv.setText(status);
        holder.mPriceTv.setText("¥ "+bean.getTotalPrice());
        initImageContainer(bean.getItems(),holder.mPContainerLl);
        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView mOrderNoTv;
        public TextView mOrderStateTv;
        public View mDivider;
        public LinearLayout mPContainerLl;
        public View mPDivider;
        public TextView mPriceTv;
        public Button mDoBtn;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mOrderNoTv = (TextView) rootView.findViewById(R.id.order_no_tv);
            this.mOrderStateTv = (TextView) rootView.findViewById(R.id.order_state_tv);
            this.mDivider = (View) rootView.findViewById(R.id.divider);
            this.mPContainerLl = (LinearLayout) rootView.findViewById(R.id.p_container_ll);
            this.mPDivider = (View) rootView.findViewById(R.id.p_divider);
            this.mPriceTv = (TextView) rootView.findViewById(R.id.price_tv);
            this.mDoBtn = (Button) rootView.findViewById(R.id.do_btn);
        }

    }
}
