package com.wind.administrator.fuck.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wind.administrator.fuck.R;

/**
 * Created by Administrator on 2017/5/22 0022.
 * 商品列表中综合排序的弹出框 包装类
 */

public class ProductSortPop extends BasePopupWindow{
    private final View.OnClickListener mClickListener;
    private TextView all_sort;
    private TextView new_sort;
    private TextView comment_sort;
    private View left_v;

    public ProductSortPop(Context context,View.OnClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
        initViews();
    }
    @Override
    protected void initViews() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.product_sort_pop_layout, null);
        all_sort= (TextView) contentView.findViewById(R.id.all_sort);
        new_sort= (TextView) contentView.findViewById(R.id.new_sort);
        comment_sort= (TextView) contentView.findViewById(R.id.comment_sort);
        all_sort.setOnClickListener(mClickListener);
        new_sort.setOnClickListener(mClickListener);
        comment_sort.setOnClickListener(mClickListener);
        contentView.findViewById(R.id.left_v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDismiss();
            }
        });
        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //1.让mPopupWindow的内部控件获取焦点
        mPopupWindow.setFocusable(true);
        //2.当内部控件可以获取焦点后，外部就不能获取焦点了（）
        //必须设置背景才有效
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //3.更新当前mPopupWindow的状态（告诉pop属性已经设置了）
        mPopupWindow.update();

    }

    /**
     * @param anchorView 锚点
     */
    @Override
    public void onShow(View anchorView){
        if(!mPopupWindow.isShowing()){
            //如果当前mPopupWindow没有显示
            mPopupWindow.showAsDropDown(anchorView,0,5);

        }
    }

}
