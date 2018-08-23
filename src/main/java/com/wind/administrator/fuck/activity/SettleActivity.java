package com.wind.administrator.fuck.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.AddOrderParams;
import com.wind.administrator.fuck.bean.AddOrderProductParams;
import com.wind.administrator.fuck.bean.RAddOrderResult;
import com.wind.administrator.fuck.bean.RReceiver;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.RShopCar;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.controller.SettleController;
import com.wind.administrator.fuck.listener.IPayOnline;
import com.wind.administrator.fuck.ui.pop.PayOnlineGetOrderPop;
import com.wind.administrator.fuck.ui.pop.PayWhenGetOrderPop;
import com.wind.administrator.fuck.util.AsyncImageLoader;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 该页面展示的是，结算页面
 */
public class SettleActivity extends BaseActivity
        implements View.OnClickListener, IPayOnline {
    public static final String CHECKED_ITEMS_KEY = "checkeditems";
    public static final String ALL_PRICE_KEY = "all_price";
    private ArrayList<RShopCar> mSerializableExtra;

    private TextView mNameTv;
    private TextView mPhoneTv;
    private TextView mAddressTv;
    private TextView mChooseAddressTv;
    private RelativeLayout mHasReceiverRl;
    private RelativeLayout mNoReceiverRl;
    private ImageView mPiv;
    private TextView mPsize;
    private LinearLayout mProductContainerLl;
    private TextView mTotalPsizeTv;
    private TextView mAllPriceTv;
    private TextView mAllPriceValTv;
    private Button mPayOnlineTv;
    private Button mPayWhengetTv;
    private TextView mPayMoneyTv;
    private SettleController mController;
    //两个请求码：用来区分选择地址和添加地址的返回值
    private static final int CHOOSE_ADDRESS_REQ = 1;
    private static final int ADD_ADDRESS_REQ = 2;
    private AsyncImageLoader mAsyncImageLoader;
    private String mAllPrice;
    private RReceiver mCurrentReceiver;
    private PayWhenGetOrderPop mPayWhenGetOrderPop;
    private LinearLayout mOrderParent;
    private PayOnlineGetOrderPop mPayOnlineGetOrderPop;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDivMessage.DEFAULT_RECEIVER_ACTION://默认收货地址
                List<RReceiver> datas = (List<RReceiver>) msg.obj;
                loadDefaultReciver(datas.isEmpty() ? null : datas.get(0));
                break;
            case IDivMessage.ADD_ORDER_ACTION:
                handleAddOrderResult((RResult) msg.obj);
                break;
        }
    }

    private void handleAddOrderResult(RResult result) {
        Log.i("mylog", "handleAddOrderResult:++++++++++++++++++++++++++++++++++++++ 进入了方法");
        if (!result.isSuccess()) {
            //下单失败
            showTip("下单失败了 " + result.getErrorMsg());
            return;
        }
        RAddOrderResult resultBean = JSON.parseObject(result.getResult(), RAddOrderResult.class);
        if (resultBean.getErrorType() == 1) {
            showTip("下单失败 :商品卖光了 ");
            return;
        } else if (resultBean.getErrorType() == 2) {
            showTip("下单失败 :系统异常 ");
            return;
        }
        //下单成功，根据支付的方式弹出不同的对话框
        if (resultBean.getPayWay() == 0) {
            //在线支付
            Log.i("mylog", "handleAddOrderResult: 在线支付");
            mPayOnlineGetOrderPop = new PayOnlineGetOrderPop(this, resultBean);
            //设置一个方法通知Pop
            mPayOnlineGetOrderPop.setListener(this);
            mPayOnlineGetOrderPop.onShow(mOrderParent);
        } else if (resultBean.getPayWay() == 1) {
            Log.i("mylog", "handleAddOrderResult: 货到付款");
            mPayWhenGetOrderPop = new PayWhenGetOrderPop(this, resultBean);
            //显示在父容器
            mPayWhenGetOrderPop.onShow(mOrderParent);
            // TODO: 2017/6/17 0017  

            //货到付款
        }


    }

    /**
     * 设置收货地址数据
     * 如果传入的对象为空，则显示没收货人的界面
     * 如果不为空，则显示有收货人界面，收货人的名称，地址，手机号
     *
     * @param bean
     */
    private void loadDefaultReciver(RReceiver bean) {
        mCurrentReceiver = bean;
        mHasReceiverRl.setVisibility(bean != null ? View.VISIBLE : View.GONE);
        mNoReceiverRl.setVisibility(bean == null ? View.VISIBLE : View.GONE);
        if (bean != null) {
            mNameTv.setText(bean.getReceiverName());
            mPhoneTv.setText(bean.getReceiverPhone());
            mAddressTv.setText(bean.getReceiverAddress());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        initData();
        initView();
        initController();
        requestDefaultReceiverData();
    }

    private void initData() {
        Intent intent = getIntent();
        //获取到传过来的商品信息
        mSerializableExtra = (ArrayList<RShopCar>) intent.getSerializableExtra(CHECKED_ITEMS_KEY);
        //获取到传过来的总价
        mAllPrice = intent.getStringExtra(ALL_PRICE_KEY);
        if (mSerializableExtra == null || mSerializableExtra.size() == 0) {
            showTip("数据异常");
            finish();
        }
    }

    //默认收货地址
    private void requestDefaultReceiverData() {
        JDApplication application = (JDApplication) getApplication();
        mController.sendAsyncMessage(IDivMessage.DEFAULT_RECEIVER_ACTION, application.getUserId(), false);
    }

    @Override
    protected void initController() {
        mController = new SettleController(this);
        mController.setIModelChangeListener(this);
    }

    private void initView() {
        //整个的父容器
        mOrderParent = (LinearLayout) findViewById(R.id.order_layout);

        mNameTv = (TextView) findViewById(R.id.name_tv);//姓名
        mPhoneTv = (TextView) findViewById(R.id.phone_tv);//电话
        mAddressTv = (TextView) findViewById(R.id.address_tv);//地址
        mChooseAddressTv = (TextView) findViewById(R.id.choose_address_tv);//选择地址
        mHasReceiverRl = (RelativeLayout) findViewById(R.id.has_receiver_rl);//已接收
        mNoReceiverRl = (RelativeLayout) findViewById(R.id.no_receiver_rl);//未接收
        mPiv = (ImageView) findViewById(R.id.piv);
        mPsize = (TextView) findViewById(R.id.psize);
        //要支付的商品信息
        mProductContainerLl = (LinearLayout) findViewById(R.id.product_container_ll);
        initImageContainer(mProductContainerLl);

        mTotalPsizeTv = (TextView) findViewById(R.id.total_psize_tv);
        mTotalPsizeTv.setText("共" + mSerializableExtra.size() + "件");
        mAllPriceTv = (TextView) findViewById(R.id.all_price_tv);
        //商品金额
        mAllPriceValTv = (TextView) findViewById(R.id.all_price_val_tv);
        //设置金额
        mAllPriceValTv.setText("¥ " + mAllPrice);
        mPayOnlineTv = (Button) findViewById(R.id.pay_online_tv);//在线支付
        mPayWhengetTv = (Button) findViewById(R.id.pay_whenget_tv);//货到付款
        //支付金额会根据支付方式的不同而变化（打折等情况）
        mPayMoneyTv = (TextView) findViewById(R.id.pay_money_tv);

        mPayOnlineTv.setOnClickListener(this);
        mPayWhengetTv.setOnClickListener(this);
    }

    /**
     * 将传递过来的选中商品数据设置
     * 给支付页面的productContainerLl
     */
    private void initImageContainer(LinearLayout iamgesContainer) {
        //1.获取图片的数据 mSerializableExtra
        //2.容器中有几个子控件（imageView）就显示几个 两者取小值
        //计算出数据的长度
        int dataSize = mSerializableExtra.size();
        //获取子控件的数量
        int childCount = iamgesContainer.getChildCount();
        //两者之间取最小
        int min = Math.min(dataSize, childCount);
        mAsyncImageLoader = AsyncImageLoader.getInstance(this);
        //3.让需要显示的控件先设置图片源 再显示出来
        for (int i = 0; i < min; i++) {
            //4.获取里面纵向的LinearLayout
            LinearLayout linearLayout = (LinearLayout) iamgesContainer.getChildAt(i);
            //5.找到里面的图片控件和文本控件
            ImageView iv = (ImageView) linearLayout.findViewById(R.id.piv);
            TextView tv = (TextView) linearLayout.findViewById(R.id.psize);
            //6.给图片和文本控件设置数据
            mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL + mSerializableExtra.get(i).getPimageUrl(), iv);
            tv.setText(" X " + mSerializableExtra.get(i).getBuyCount());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_online_tv://在线支付
            case R.id.pay_whenget_tv://货到付款
                mPayOnlineTv.setSelected(v == mPayOnlineTv);
                mPayWhengetTv.setSelected(v == mPayWhengetTv);
                break;
        }
    }

    /**
     * 选择地址按钮
     *
     * @param view
     */
    public void chooseAddress(View view) {
        Intent intent = new Intent(SettleActivity.this, ChooseAddressActivity.class);
        startActivityForResult(intent, CHOOSE_ADDRESS_REQ);
        Log.i("mylog", "chooseAddress: >>>>>>>>>>>>>>>>>.点击选择地址");

    }

    /**
     * 添加地址按钮
     *
     * @param view
     */
    public void addAddress(View view) {
        Intent intent = new Intent(SettleActivity.this, AddAddressActivity.class);
        startActivityForResult(intent, ADD_ADDRESS_REQ);
        Log.i("mylog", "addAddress: >>>>>>>>>>>>>>>>>.点击添加地址");
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data        从选择地址中，传过来的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //因为进入界面后直接返回就有可能是空，则data没有数据
        if (data != null) {
            if (requestCode == CHOOSE_ADDRESS_REQ || requestCode == ADD_ADDRESS_REQ) {
                RReceiver receiver = (RReceiver) data.getSerializableExtra("RECEIVER");
                //重新设置数据
                loadDefaultReciver(receiver);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 提交订单按钮
     *
     * @param view
     */
    public void submitClick(View view) {
        //判断是否有收获地址
        if (mCurrentReceiver == null) {
            showTip("请输入收货地址！");
            return;
        }
        //判断是否选择支付方式
        if (!mPayOnlineTv.isSelected() && !mPayWhengetTv.isSelected()) {
            showTip("请选择支付方式！");
            return;
        }
        //发送网络请求提交订单
        mController.sendAsyncMessage(IDivMessage.ADD_ORDER_ACTION, buildAddOraerParams());
    }

    private AddOrderParams buildAddOraerParams() {
        ArrayList<AddOrderProductParams> products = new ArrayList<>();
        for (int i = 0; i < mSerializableExtra.size(); i++) {
            RShopCar bean = mSerializableExtra.get(i);
            AddOrderProductParams params = new AddOrderProductParams();
            params.setPid(bean.getPid());
            params.setType(bean.getPversion());
            params.setBuyCount(bean.getBuyCount());
            products.add(params);
        }
        //支付方式
        int payWay = mPayOnlineTv.isSelected() ? 0 : 1;
        JDApplication application = (JDApplication) getApplication();
        AddOrderParams result = new AddOrderParams(products, mCurrentReceiver.getId(), payWay, application.getUserId());
        return result;
    }
// TODO: 2017/6/17 0017

    /**
     * 在线支付确定按钮 接口方法
     * tn 要传递的天号  （流水号）
     */
    @Override
    public void onSubmit(String tn) {
        //1.隐藏弹出框
        if (mPayOnlineGetOrderPop != null) {
            mPayOnlineGetOrderPop.onDismiss();
        }
        //2.将数据传递给新的页面
        Intent intent = new Intent(this, AliPayActivity.class);
        //传递天号
        intent.putExtra(AliPayActivity.TN_KEY, tn);
        startActivity(intent);

        //3.隐藏Activity
        finish();

    }

    /**
     * 在线支付的取消按钮 接口方法
     */
    @Override
    public void onCancel() {
        //1.隐藏弹出框
        if (mPayOnlineGetOrderPop != null) {
            mPayOnlineGetOrderPop.onDismiss();
        }
        //2.隐藏Activity
        finish();
        //3.提醒用户继续支付
        showTip("支付失败！请到订单列表中支付！");
    }
}
