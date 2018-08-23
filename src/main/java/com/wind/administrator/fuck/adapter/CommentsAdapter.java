package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RComments;
import com.wind.administrator.fuck.bean.RRecommandProduct;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.databinding.CommentItemLayoutBinding;
import com.wind.administrator.fuck.databinding.RecommendItemLayoutBinding;
import com.wind.administrator.fuck.ui.RatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 * dataBinding应用
 */

public class CommentsAdapter extends JDBaseAdapter<RComments> {


    private RatingBar mRatingBar;

    /**
     * 构造器
     *
     * @param context
     */
    public CommentsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentItemLayoutBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(mInflater,R.layout.comment_item_layout,parent,false);
            convertView = binding.getRoot();//获取layout标签对象的View对象
            convertView.setTag(binding);
        } else {
            binding = (CommentItemLayoutBinding) convertView.getTag();
        }
        RComments bean = mDatas.get(position);
        binding.setBean(bean);
        mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL + bean.getUserImg(),binding.iconIv);
//        ((RatingBar)binding.ratingBar).setRating(bean.getRate());
        mRatingBar = (RatingBar) binding.getRoot().findViewById(R.id.rating_bar);
        mRatingBar.setRating(bean.getRate());
        binding.contentTv.setText(bean.getComment());
        binding.buytimeTv.setText("购买时间 : "+bean.getBuyTime());
        binding.lovecountTv.setText("喜欢("+bean.getLoveCount()+")");
        binding.subcommentTv.setText("回复（"+bean.getSubComment()+"）");
        initImageContainer(bean.getImgUrls(),binding.iamgesContainer);
        return convertView;
    }

    private void initImageContainer(String imgUrls, LinearLayout iamgesContainer) {
        //1.获取图片的数据
        List<String> imageUrls = JSON.parseArray(imgUrls, String.class);
        //2.容器中有几个子控件（imageView）就显示几个 两者取小值
                //计算出数据的长度
        int dataSize = imageUrls.size();
                //获取子控件的数量
        int childCount = iamgesContainer.getChildCount();
                //两者之间取最小
        int min = Math.min(dataSize, childCount);
        //3.让所有的图片控件隐藏
        for (int i = 0; i < childCount; i++) {
            iamgesContainer.getChildAt(i).setVisibility(View.INVISIBLE);
        }
        //4.让需要显示的控件先设置图片源 再显示出来
        for (int i = 0; i < min; i++) {
            ImageView iv = (ImageView) iamgesContainer.getChildAt(i);
            mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL+imageUrls.get(i),iv);
            iv.setVisibility(View.VISIBLE);
        }
        iamgesContainer.setVisibility(dataSize>0?View.VISIBLE:View.GONE);
    }


}

























