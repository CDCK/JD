package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RReceiver;
import com.wind.administrator.fuck.listener.IDeleteReceiverListener;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class ReceiverAdapter extends JDBaseAdapter<RReceiver> {


    private IDeleteReceiverListener mListener;

    /**
     * 构造器
     *
     * @param context
     */
    public ReceiverAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.choose_address_item_layout, parent, false);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.phone_tv);
            holder.addressTv = (TextView) convertView.findViewById(R.id.address_tv);
            holder.deleteTv = (TextView) convertView.findViewById(R.id.delete_tv);
            holder.isDeafultIv = (ImageView) convertView.findViewById(R.id.isDeafult_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RReceiver bean = mDatas.get(position);
        holder.nameTv.setText(bean.getReceiverName());
        holder.phoneTv.setText(bean.getReceiverPhone());
        holder.addressTv.setText(bean.getReceiverAddress());
        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除某个收货人地址
                if (mListener != null) {
                    mListener.onReceiverDeleted(bean.getId());
                    Log.i("mylog", "ReceiverAdapter------onClick: bean.getId()=====================" + bean.getId());
                }
            }
        });
        //如果是默认收货地址，就显示图片
        holder.isDeafultIv.setVisibility(bean.getIsDefault() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public void setListener(IDeleteReceiverListener iDeleteReceiverListener) {
        mListener = iDeleteReceiverListener;
    }


    class ViewHolder {
        public ImageView isDeafultIv;
        public TextView nameTv;
        public TextView phoneTv;
        public TextView addressTv;
        public TextView deleteTv;
    }
}
