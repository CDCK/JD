package com.wind.administrator.fuck.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RAddOrderResult;

/**
 * Created by Administrator on 2017/5/22 0022.
 * 货到付款弹出框
 */

public class PayWhenGetOrderPop extends BasePopupWindow implements View.OnClickListener {


    private final RAddOrderResult mBean;
    private TextView mOrderNoTv;
    private TextView mTotalPriceTv;
    private TextView mFreightTv;
    private TextView mActualPriceTv;
    private Button mSureBtn;

    public PayWhenGetOrderPop(Context context, RAddOrderResult resultBean) {
        mContext = context;
        mBean = resultBean;
        initViews();
    }

    @Override
    protected void initViews() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.paywhenget_order_pop, null);
        //找到弹出框内部所有的控件
        mOrderNoTv = (TextView) contentView.findViewById(R.id.order_no_tv);
        mTotalPriceTv = (TextView) contentView.findViewById(R.id.total_price_tv);
        mFreightTv = (TextView) contentView.findViewById(R.id.freight_tv);
        mActualPriceTv = (TextView) contentView.findViewById(R.id.actual_price_tv);
        mSureBtn = (Button) contentView.findViewById(R.id.sure_btn);
        mSureBtn.setOnClickListener(this);
        //给控件赋值
        mOrderNoTv.setText("订单编号： "+mBean.getOrderNum());
        mTotalPriceTv.setText("总价 ：¥ "+mBean.getAllPrice());
        mFreightTv.setText("运费：¥ "+mBean.getFreight());
        mActualPriceTv.setText("实付：¥ "+mBean.getTotalPrice());

        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
    public void onShow(View anchorView) {
        if (!mPopupWindow.isShowing()) {
            //如果当前mPopupWindow没有显示
            mPopupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        }
    }

    /**
     * 点击确定按钮
     * @param v
     */
    @Override
    public void onClick(View v) {

    }
}
