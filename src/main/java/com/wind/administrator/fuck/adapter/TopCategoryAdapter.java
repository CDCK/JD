package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RSecondKill;
import com.wind.administrator.fuck.bean.RTopCateGory;
import com.wind.administrator.fuck.cons.NetworkConstant;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class TopCategoryAdapter extends JDBaseAdapter<RTopCateGory> {
        public int mCurrentTapIndex = -1;//记录点击的item的索引

     /**
     * 构造器
     *
     * @param context
     */
    public TopCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.top_category_item_layout, parent, false);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.dividerView = convertView.findViewById(R.id.divider);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RTopCateGory bean = mDatas.get(position);
        holder.nameTv.setText(bean.getName());
        //TOP 3.根据当前的索引（mCurrentTapIndex）来显示新的界面
        if(position == mCurrentTapIndex){//该Item被点击了
            holder.dividerView.setVisibility(View.INVISIBLE);
            holder.nameTv.setBackgroundResource(R.drawable.tongcheng_all_bg01);
        }else{//没被点击的
            holder.dividerView.setVisibility(View.VISIBLE);
            holder.nameTv.setBackgroundColor(0xFFFFFFFF);
        }
        return convertView;
    }
    class ViewHolder {
        TextView nameTv;
        View dividerView;
    }

    @Override
    public RTopCateGory getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }
}
