package com.wind.administrator.fuck.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RPayResult;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.SMokePayParams;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.AliPayController;
import com.wind.administrator.fuck.controller.RPayInfo;
import com.wind.administrator.fuck.listener.IPayResultListener;
import com.wind.administrator.fuck.ui.pop.AliPayDialog;

import org.json.JSONObject;

/**
 * 模拟 支付宝支付页面
 */
public class AliPayActivity extends BaseActivity implements IPayResultListener {
    public static final String TN_KEY = "tn";
    private String mTn;
    private TextView mPayPriceTv;
    private TextView mOrderDescValTv;
    private TextView mDealTypeValTv;
    private TextView mDealTimeValTv;
    private TextView mDealNoValTv;
    private AliPayDialog mAliPayDialog;


    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDivMessage.GET_PAYINFO_ACTION:
                handlePayInfoResult((RResult) msg.obj);
                break;
            case IDivMessage.MOKE_PAY_ACTION:
                handlePayResult((RResult) msg.obj);
                break;
        }
    }


    /**
     * 处理支付的结果
     * @param result
     */
    private void handlePayResult(RResult result) {
        if (result.isSuccess()) {
            //告诉用户支付成功，并打开新的订单详情页面
            showTip("支付成功!");
            // TODO: 2017/6/19 0019 跳到订单详情页面
            Intent intent = new Intent(this,OrderDetailsActivity.class);
            RPayResult bean = JSON.parseObject(result.getResult(), RPayResult.class);
            //传入一个订单的 oid
            intent.putExtra(OrderDetailsActivity.OID_KEY,bean.getOid());
            startActivity(intent);
            //关闭页面
            finish();
        } else {
            //隐藏对话框，提醒用户失败的原因
            mAliPayDialog.dismiss();
            showTip(result.getErrorMsg());
        }
    }

    private void handlePayInfoResult(RResult resultBean) {
        if (resultBean.isSuccess()) {
            //后台问题，有一些是失败的
            RPayInfo payInfo = JSON.parseObject(resultBean.getResult(), RPayInfo.class);
            showPayInfoView(payInfo);
        } else {
            showTip(resultBean.getErrorMsg() + "请到订单列表中继续支付");
            finish();
        }
    }

    /**
     * 显示支付信息的页面
     * @param payInfo
     */
    private void showPayInfoView(RPayInfo payInfo) {
        mPayPriceTv.setText("¥ " + payInfo.getTotalPrice());
        mDealNoValTv.setText(payInfo.getPname());
        mDealTypeValTv.setText("担保交易");
        mDealTimeValTv.setText(payInfo.getPayTime());
        mDealNoValTv.setText(payInfo.getOinfo());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_pay);
        initData();
        initController();
        initView();
        requestMainData();
    }

    private void requestMainData() {
        JDApplication application = (JDApplication) getApplication();
        mController.sendAsyncMessage(IDivMessage.GET_PAYINFO_ACTION, application.getUserId(), mTn);
    }

    @Override
    protected void initController() {
        mController = new AliPayController(this);
        mController.setIModelChangeListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        mTn = intent.getStringExtra(TN_KEY);
        if (TextUtils.isEmpty(mTn)) {
            showTip("支付失败，请到订单列表继续支付！");
            finish();
        }


    }

    private void initView() {
        mPayPriceTv = (TextView) findViewById(R.id.pay_price_tv);
        mOrderDescValTv = (TextView) findViewById(R.id.order_desc_val_tv);
        mDealTypeValTv = (TextView) findViewById(R.id.deal_type_val_tv);
        mDealTimeValTv = (TextView) findViewById(R.id.deal_time_val_tv);
        mDealNoValTv = (TextView) findViewById(R.id.deal_no_val_tv);
    }

    //立即付款按钮
    public void payClick(View view) {
        mAliPayDialog = new AliPayDialog(this);
        mAliPayDialog.setListener(this);
        mAliPayDialog.show();

    }

    //点击付款后的回调方法
    @Override
    public void onPayResult(String account, String pwd, String payPwd) {
        //5个参数
        JDApplication application = (JDApplication) getApplication();
        SMokePayParams params = new SMokePayParams(account, pwd, payPwd, mTn, application.getUserId());
        mController.sendAsyncMessage(IDivMessage.MOKE_PAY_ACTION, params);
    }
}
