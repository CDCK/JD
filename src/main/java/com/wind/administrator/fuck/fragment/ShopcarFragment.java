package com.wind.administrator.fuck.fragment;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.SettleActivity;
import com.wind.administrator.fuck.adapter.ShopCarAdapter;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.RShopCar;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.ShopCarController;
import com.wind.administrator.fuck.listener.IShopCarDeleteListener;
import com.wind.administrator.fuck.ui.FlexiListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 * 购物车Fragment
 */

public class ShopcarFragment extends BaseFragment
        implements View.OnClickListener, AdapterView.OnItemClickListener, IShopCarDeleteListener {
    private FlexiListView mShopcarLv;
    private CheckBox mAllCbx;
    private TextView mAllMoneyTv;
    private TextView mSettleTv;
    private ShopCarAdapter mShopCarAdapter;
    private View mNullView;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDivMessage.SHOP_CAR_ACTION:
                loadShopCarsListView((List<RShopCar>) msg.obj);
                break;
            case IDivMessage.DELETE_SHOPCAR_ACTION:
                handleDeleteShopCar((RResult) msg.obj);
                break;
        }
    }

    private void handleDeleteShopCar(RResult resultBean) {
        if (resultBean.isSuccess()) {
            showTip("删除购物车成功！");
            requestShopCarDatas();
        } else {
            showTip(resultBean.getErrorMsg());
        }

    }


    private void loadShopCarsListView(List<RShopCar> datas) {
        if (!datas.isEmpty()) {
            mShopCarAdapter.setDatas(datas);
            mShopCarAdapter.notifyDataSetChanged();
        }
        mShopcarLv.setVisibility(!datas.isEmpty() ? View.VISIBLE : View.GONE);
        mNullView.setVisibility(datas.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcar, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mShopcarLv = (FlexiListView) view.findViewById(R.id.shopcar_lv);
        mShopCarAdapter = new ShopCarAdapter(getActivity());
        mShopCarAdapter.setListener(this);
        mShopcarLv.setAdapter(mShopCarAdapter);
        mShopcarLv.setOnItemClickListener(this);

        mAllCbx = (CheckBox) view.findViewById(R.id.all_cbx);//全选按钮
        mAllCbx.setOnClickListener(this);

        mNullView = view.findViewById(R.id.null_view);


        mAllMoneyTv = (TextView) view.findViewById(R.id.all_money_tv);//总金额
        mSettleTv = (TextView) view.findViewById(R.id.settle_tv);//去结算


        mSettleTv.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        requestShopCarDatas();
    }

    private void requestShopCarDatas() {
        JDApplication application = (JDApplication) getActivity().getApplication();
        Long userId = application.getUserId();
        Log.i("mylog", "requestShopCarDatas: JDApplication UserID >>>>>>>>>>>>>>>>>>>>>>>"+userId);
        mController.sendAsyncMessage(IDivMessage.SHOP_CAR_ACTION, userId);

    }

    @Override
    protected void initController() {
        mController = new ShopCarController(getActivity());
        mController.setIModelChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settle_tv://去结算
                Log.i("mylog", "onClick: 点击去结算按钮跳转到SettleActivity====================》》》》》》》》");
                //获取选中的商品
                ArrayList<RShopCar> checkedItems = (ArrayList<RShopCar>) mShopCarAdapter.getCheckedItems();
                //判断用户是否选中商品
                if(checkedItems==null){
                    showTip("请先选择要结算的商品");
                }
                Intent intent = new Intent(getActivity(), SettleActivity.class);
                //将选中商品的数据传递到结算页面
                intent.putExtra(SettleActivity.CHECKED_ITEMS_KEY,checkedItems);
                double checkProductPriceCount = mShopCarAdapter.getCheckProductPriceCount();
                Log.i("mylog", "onClick: checkProductPriceCount---商品的总价>>>>>>>>>"+checkProductPriceCount);
                intent.putExtra(SettleActivity.ALL_PRICE_KEY,String.valueOf(checkProductPriceCount));
                startActivity(intent);
                break;
            case R.id.all_cbx://全选
                mShopCarAdapter.setAllCheck();
                //点击全选完后，再调用
                showCheckProductInfo();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //刷新某个item的数据
        mShopCarAdapter.setItemChecked(position);
        //判断 如果列表所有item都被选中，全选按钮就设置为true 反之false
        boolean flag = mShopCarAdapter.ifAllChecked();
        Log.i("mylog", "-----------" + flag);
        mAllCbx.setChecked(flag);
        //修改总金额 和结算按钮文本
        showCheckProductInfo();

    }

    /**
     * 显示选中商品的信息
     */
    private void showCheckProductInfo() {
        mAllMoneyTv.setText("总额： ¥ " + mShopCarAdapter.getCheckProductPriceCount());
        mSettleTv.setText("去结算 (" + mShopCarAdapter.getCheckProductCount() + ")");
    }

    @Override
    public void onItemDeleted(Long shopcarId) {
        //发送一个删除购物车的网络请求
        JDApplication application = (JDApplication) getActivity().getApplication();
        Long userId = application.getUserId();
        mController.sendAsyncMessage(IDivMessage.DELETE_SHOPCAR_ACTION, userId, shopcarId);
    }
}
