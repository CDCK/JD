package com.wind.administrator.fuck.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.adapter.OrdersContainerAdapter;
import com.wind.administrator.fuck.listener.JDPagerChangeListener;

/**
 * 点击全部订单进入 订单列表页面
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mAllOrderLl;
    private LinearLayout mWaitPayLl;
    private LinearLayout mWaitReceiveLl;
    private LinearLayout mWaitSureLl;
    private ViewPager mContainerVp;
    private OrdersContainerAdapter mOrdersControllerAdapter;
    private View mAllOrderView;
    private View mWaitPayView;
    private View mWaitReceiveView;
    private View mWaitSureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
    }

    private void initView() {
        mAllOrderLl = (LinearLayout) findViewById(R.id.all_order_ll);
        mAllOrderLl.setOnClickListener(this);
        mWaitPayLl = (LinearLayout) findViewById(R.id.wait_pay_ll);
        mWaitPayLl.setOnClickListener(this);
        mWaitReceiveLl = (LinearLayout) findViewById(R.id.wait_receive_ll);
        mWaitReceiveLl.setOnClickListener(this);
        mWaitSureLl = (LinearLayout) findViewById(R.id.wait_sure_ll);
        mWaitSureLl.setOnClickListener(this);
        //初始化指示器
        mAllOrderView = (View) findViewById(R.id.all_order_view);
        mWaitPayView = (View) findViewById(R.id.wait_pay_view);
        mWaitReceiveView = (View) findViewById(R.id.wait_receive_view);
        mWaitSureView = (View) findViewById(R.id.wait_sure_view);
        //初始化容器
        mContainerVp = (ViewPager) findViewById(R.id.vp);
        mOrdersControllerAdapter = new OrdersContainerAdapter(getSupportFragmentManager());
        mContainerVp.setAdapter(mOrdersControllerAdapter);
        mContainerVp.addOnPageChangeListener(new JDPagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeIndicatorView(mAllOrderView);
                        break;
                    case 1:
                        changeIndicatorView(mWaitPayView);
                        break;
                    case 2:
                        changeIndicatorView(mWaitReceiveView);
                        break;
                    case 3:
                        changeIndicatorView(mWaitSureView);
                        break;
                }
            }
        });

    }
    private void changeIndicatorView(View view){
        mAllOrderView.setVisibility(view==mAllOrderView?View.VISIBLE:View.INVISIBLE);
        mWaitPayView.setVisibility(view==mWaitPayView?View.VISIBLE:View.INVISIBLE);
        mWaitReceiveView.setVisibility(view==mWaitReceiveView?View.VISIBLE:View.INVISIBLE);
        mWaitSureView.setVisibility(view==mWaitSureView?View.VISIBLE:View.INVISIBLE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_order_ll:
                mContainerVp.setCurrentItem(0, true);
                break;
            case R.id.wait_pay_ll:
                mContainerVp.setCurrentItem(1, true);
                break;
            case R.id.wait_receive_ll:
                mContainerVp.setCurrentItem(2, true);
                break;
            case R.id.wait_sure_ll:
                mContainerVp.setCurrentItem(3, true);
                break;
        }
    }
}
