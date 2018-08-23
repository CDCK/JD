package com.wind.administrator.fuck.fragment.details;


import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.ProductDetailsActivity;
import com.wind.administrator.fuck.adapter.GoodCommentsAdapter;
import com.wind.administrator.fuck.adapter.ProductBannerAdapter;
import com.wind.administrator.fuck.adapter.ProductVersionAdapter;
import com.wind.administrator.fuck.bean.RGoodComment;
import com.wind.administrator.fuck.bean.RProductInfo;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.ProductDetailsController;
import com.wind.administrator.fuck.fragment.BaseFragment;
import com.wind.administrator.fuck.listener.INumberInputListener;
import com.wind.administrator.fuck.listener.JDPagerChangeListener;
import com.wind.administrator.fuck.ui.NumberInputView;
import com.wind.administrator.fuck.util.FixedViewUtil;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


/**
 * Created by xmg on 2017/5/15.
 * 商品信息
 */
public class ProductInfoFragment extends BaseFragment implements View.OnClickListener, INumberInputListener {

    private AutoScrollViewPager mProductBannerVp;
    private TextView mVpIndicTv;
    private TextView mNameTv;
    private TextView mSelfSaleTv;
    private TextView mDescTv;
    private TextView mRecommendBuyTv;
    private TextView mPriceTv;
    private TextView mTipTv;
    private ListView mProductVersionsLv;
    private TextView mGoodRateTip;
    private TextView mGoodRateTv;
    private TextView mGoodCommentTv;
    private ListView mGoodCommentLv;
    private ScrollView mScrollview;
    private ProductBannerAdapter mProductBannerAdapter;
    private ProductVersionAdapter mProductVersionAdapter;
    private NumberInputView mNumInputView;
    private GoodCommentsAdapter mGoodCommentsAdapter;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDivMessage.PRODUCT_INFO_ACTION:
                handleProductInfoResult((RResult) msg.obj);
                break;
            case IDivMessage.GOOD_COMMENT_ACTION:
                handleGoodComment((List<RGoodComment>) msg.obj);
                break;
        }
    }

    private void handleGoodComment(List<RGoodComment> datas) {
        mGoodCommentsAdapter.setDatas(datas);
        mGoodCommentsAdapter.notifyDataSetChanged();
        FixedViewUtil.setListViewHeightBasedOnChildren(mGoodCommentLv);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_info, container, false);
        initView(view);

        return view;
    }

    private void handleProductInfoResult(RResult result) {
        if (result.isSuccess()) {
            RProductInfo bean = JSON.parseObject(result.getResult(), RProductInfo.class);
            if (bean != null) {
                //1.加载广告栏
                showBanner(bean.getImgUrls());
                //2.实现商品型号显示
                showProductTypes(bean.getTypeList());
                //3.数量模块的实现
                //4.显示一些简单的控件
                showHandleSimpleComponents(bean);
            }
        } else {
            showTip(result.getErrorMsg());
            getActivity().finish();
        }

    }

    /**
     * 显示出其他的控件
     */
    private void showHandleSimpleComponents(RProductInfo bean) {
        mNameTv.setText(bean.getName());
        mSelfSaleTv.setVisibility(bean.isIfSaleOneself() ? View.VISIBLE : View.GONE);
        mDescTv.setText(bean.getRecomProduct());
        mRecommendBuyTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//点击购买
        //TODO
        mPriceTv.setText("¥ " + bean.getPrice() + "");//价格
        mNumInputView.setMax(bean.getStockCount());
        mGoodRateTv.setText(bean.getFavcomRate() + "%好评");
        mGoodCommentTv.setText(bean.getCommentCount() + "人评价");
    }

    /**
     * 商品型号列表显示方法
     *
     * @param typeListJson JSON数组
     */
    private void showProductTypes(String typeListJson) {
        final List<String> pVersion = JSON.parseArray(typeListJson, String.class);
        mProductVersionAdapter.setDatas(pVersion);
        mProductVersionAdapter.notifyDataSetChanged();
        FixedViewUtil.setListViewHeightBasedOnChildren(mProductVersionsLv);
    }

    /**
     * 显示商品的图片广告栏
     *
     * @param imgUrls
     */
    private void showBanner(String imgUrls) {
        //图片的地址
        final List<String> imageUrls = JSON.parseArray(imgUrls, String.class);
        mProductBannerAdapter.setDatas(imageUrls);
        mProductBannerAdapter.notifyDataSetChanged();
        mProductBannerVp.setOnPageChangeListener(new JDPagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //设置指示器
                mVpIndicTv.setText((position + 1) + "/" + imageUrls.size());
            }
        });
        //设置指示器
        mVpIndicTv.setText("1/" + imageUrls.size());//默认就是 1
        //让广告实现滚动
        if (!imageUrls.isEmpty()) {
            mProductBannerVp.startAutoScroll();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        requestProductInfoDatas();
        requestGoodCommentDatas();
    }

    private void requestProductInfoDatas() {
        //先获取到ProductDetailsActivity，再从ProductDetailsActivity中获取商品ID
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        mController.sendAsyncMessage(IDivMessage.PRODUCT_INFO_ACTION, activity.mProductId);
    }

    /**
     * 好评
     */
    private void requestGoodCommentDatas() {
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        mController.sendAsyncMessage(IDivMessage.GOOD_COMMENT_ACTION, activity.mProductId);

    }

    @Override
    protected void initController() {
        mController = new ProductDetailsController(getActivity());
        mController.setIModelChangeListener(this);
    }

    private void initView(View view) {

        mVpIndicTv = (TextView) view.findViewById(R.id.vp_indic_tv);
        mNameTv = (TextView) view.findViewById(R.id.name_tv);
        mSelfSaleTv = (TextView) view.findViewById(R.id.self_sale_tv);
        mDescTv = (TextView) view.findViewById(R.id.desc_tv);

        mPriceTv = (TextView) view.findViewById(R.id.price_tv);
        mTipTv = (TextView) view.findViewById(R.id.tip_tv);


        mGoodRateTip = (TextView) view.findViewById(R.id.good_rate_tip);
        mGoodRateTv = (TextView) view.findViewById(R.id.good_rate_tv);
        //好评
        mGoodCommentTv = (TextView) view.findViewById(R.id.good_comment_tv);
        mGoodCommentLv = (ListView) view.findViewById(R.id.good_comment_lv);
        mGoodCommentsAdapter = new GoodCommentsAdapter(getActivity());
        mGoodCommentLv.setAdapter(mGoodCommentsAdapter);

        mScrollview = (ScrollView) view.findViewById(R.id.scrollview);
        mRecommendBuyTv = (TextView) view.findViewById(R.id.recommend_buy_tv);
        //广告栏图片
        mProductBannerVp = (AutoScrollViewPager) view.findViewById(R.id.product_banner_vp);
        mProductBannerAdapter = new ProductBannerAdapter(getActivity());
        mProductBannerVp.setAdapter(mProductBannerAdapter);
        //商品的型号
        mProductVersionsLv = (ListView) view.findViewById(R.id.product_versions_lv);
        mProductVersionAdapter = new ProductVersionAdapter(getActivity());
        mProductVersionsLv.setAdapter(mProductVersionAdapter);
        mProductVersionsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProductVersionAdapter.mCurrentTapIndex = position;
                mProductBannerAdapter.notifyDataSetChanged();
                //获取当前选中的字符串（ 点击加入购物车--》商品的型号）
                String version = mProductVersionAdapter.getItem(position);
                ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
                //将商品的型号放入到ProductDetailsActivity中的mProductVersion中
                activity.mProductVersion = version;
            }
        });
        //选择商品数量控件
        mNumInputView = (NumberInputView) view.findViewById(R.id.number_input_et);
        mNumInputView.setListener(this);

        mRecommendBuyTv.setOnClickListener(this);

    }

    /**
     * 购买按钮事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recommend_buy_tv:

                break;
        }
    }

    @Override
    public void onTextChange(int buyCount) {
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        activity.mBuyCount = buyCount ;
    }
}
