package com.wind.administrator.fuck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RComments;
import com.wind.administrator.fuck.bean.RGoodComment;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.databinding.CommentItemLayoutBinding;
import com.wind.administrator.fuck.ui.RatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20 0020.
 * dataBinding应用
 */

public class GoodCommentsAdapter extends JDBaseAdapter<RGoodComment> {


    private RatingBar mRatingBar;

    /**
     * 构造器
     *
     * @param context
     */
    public GoodCommentsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null ;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.good_comment_item_layout,parent,false);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.contentTv = (TextView) convertView.findViewById(R.id.content_tv);
            holder.mRatingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
            holder.imageContainersLl = (LinearLayout) convertView.findViewById(R.id.iamges_container);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        RGoodComment bean = mDatas.get(position);
        holder.nameTv.setText(bean.getUserName());
        holder.contentTv.setText(bean.getComment());
        holder.mRatingBar.setRating(bean.getRate());
        initImageContainer(bean.getImgUrls(),holder.imageContainersLl);
        return convertView;
    }

   class ViewHolder{

       public RatingBar mRatingBar;
       public TextView contentTv;
       public LinearLayout imageContainersLl;
       TextView nameTv;
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

























