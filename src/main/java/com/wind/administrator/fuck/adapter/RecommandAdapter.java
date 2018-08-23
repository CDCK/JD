package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RRecommandProduct;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.databinding.RecommendItemLayoutBinding;

/**
 * Created by Administrator on 2017/5/20 0020.
 * dataBinding应用
 */

public class RecommandAdapter extends JDBaseAdapter<RRecommandProduct> {


     /**
     * 构造器
     *
     * @param context
     */
    public RecommandAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RecommendItemLayoutBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(mInflater,R.layout.recommend_item_layout,parent,false);
            convertView = binding.getRoot();//获取layout标签对象的View对象
            convertView.setTag(binding);
        } else {
            binding = (RecommendItemLayoutBinding) convertView.getTag();
        }
        RRecommandProduct bean = mDatas.get(position);
        binding.setBean(bean);
        mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL + bean.getIconUrl(), binding.iconIv);
//        binding.nameTv.setText(bean.getName());//在layout布局中利用databing已经赋值

        binding.priceTv.setText("¥ " + bean.getPrice());
        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getProductId():0;
    }
}


















