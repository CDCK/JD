package com.wind.administrator.fuck.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.wind.administrator.fuck.activity.ProductDetailsActivity;
import com.wind.administrator.fuck.adapter.HomePagerAdapter;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.adapter.RecommandAdapter;
import com.wind.administrator.fuck.adapter.SecondKillAdapter;
import com.wind.administrator.fuck.bean.RBanner;
import com.wind.administrator.fuck.bean.RRecommandProduct;
import com.wind.administrator.fuck.bean.RSecondKill;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.HomeController;
import com.wind.administrator.fuck.listener.JDPagerChangeListener;
import com.wind.administrator.fuck.ui.HorizontalListView;
import com.wind.administrator.fuck.util.FixedViewUtil;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.wind.administrator.fuck.R.id.search_et;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ImageView mScanIv;
    private EditText mSearchEt;
    private ImageView mMessageIv;
    private AutoScrollViewPager mAdVp;
    private LinearLayout mAdIndicator;
    private RelativeLayout mAdRl;
    private LinearLayout mLl;
    private ScrollView mScrollbar;
    private HomePagerAdapter mHomePagerAdapter;
    private HorizontalListView mSecKillLv;//秒杀模块
    private SecondKillAdapter mSecondKillAdapter;
    private GridView mRecommend_gv;
    private RecommandAdapter mRecommandAdapter;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDivMessage.ADBANNER_ACTION:
                refreshAdBanner(msg);
                break;
            case IDivMessage.SECOND_KILL_ACTION:
                showSecondKillLv(msg);
                break;
            case IDivMessage.RECOMMAND_PRODUCT_ACTION:
                showRecommandGv((List<RRecommandProduct>) msg.obj);
                break;
        }
    }

    private void showRecommandGv(List<RRecommandProduct> datas) {
        mRecommandAdapter.setDatas(datas);
        mRecommandAdapter.notifyDataSetChanged();
        FixedViewUtil.setGridViewHeightBasedOnChildren(mRecommend_gv, 2);//设置成两列
    }

    private void showSecondKillLv(Message msg) {
        List<RSecondKill> datas = (List<RSecondKill>) msg.obj;
        mSecondKillAdapter.setDatas(datas);
        mSecondKillAdapter.notifyDataSetChanged();

    }

    private void refreshAdBanner(Message msg) {
        List<RBanner> datas = (List<RBanner>) msg.obj;
        mHomePagerAdapter.setDatas(datas);
        mHomePagerAdapter.notifyDataSetChanged();
        if (!datas.isEmpty()) {//不为空
            mAdRl.setVisibility(View.VISIBLE);
            //当有数据的时候启动（图片自动切换）
            mAdVp.startAutoScroll();
            //添加指示器（四个点）
            initAdBannerIndicator(datas.size());
        }
    }

    /**
     * 初始化指示器
     *
     * @param size
     */
    private void initAdBannerIndicator(int size) {
        //for循环
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(getActivity());
            LinearLayout.LayoutParams parpams = new LinearLayout.LayoutParams(15, 15);
            parpams.setMargins(30, 0, 0, 0);
            iv.setLayoutParams(parpams);
            iv.setBackgroundResource(R.drawable.ad_indicator_bg);
            mAdIndicator.addView(iv);
        }
        //默认指示器（第一个）
        //mAdIndicator.getChildAt(0).setSelected(true);
        changeAdindicator(0);
    }

    @Nullable
    /**
     * 粘贴布局文件
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    /**
     * 获取子控件
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        reuesteAdBannerDatas();
        reqestSecondKillDatas();
        requestRecommandDatas();
    }

    @Override
    protected void initController() {
        mController = new HomeController(getActivity());
        mController.setIModelChangeListener(this);
    }

    private void requestRecommandDatas() {
        mController.sendAsyncMessage(IDivMessage.RECOMMAND_PRODUCT_ACTION);
    }

    /**
     * 秒杀模块
     */
    private void reqestSecondKillDatas() {
        mController.sendAsyncMessage(IDivMessage.SECOND_KILL_ACTION);
    }

    private void reuesteAdBannerDatas() {
        mController.sendAsyncMessage(IDivMessage.ADBANNER_ACTION);
    }

    private void initView(View view) {
        mScanIv = (ImageView) view.findViewById(R.id.scan_iv);
        mSearchEt = (EditText) view.findViewById(search_et);
        mMessageIv = (ImageView) view.findViewById(R.id.message_iv);
        mAdVp = (AutoScrollViewPager) view.findViewById(R.id.ad_vp);

        mHomePagerAdapter = new HomePagerAdapter(getActivity());
        mAdVp.setAdapter(mHomePagerAdapter);
        mAdVp.addOnPageChangeListener(new JDPagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //底部的指示器（几个小点）随着顶部的图片切换而切换
                //找到LinearLayout容器
                changeAdindicator(position);
            }
        });
        mAdIndicator = (LinearLayout) view.findViewById(R.id.ad_indicator);
        mSecKillLv = (HorizontalListView) view.findViewById(R.id.sec_kill_lv);
        mSecondKillAdapter = new SecondKillAdapter(getActivity());
        mSecKillLv.setAdapter(mSecondKillAdapter);
        mAdRl = (RelativeLayout) view.findViewById(R.id.ad_rl);
        mLl = (LinearLayout) view.findViewById(R.id.ll);
        mScrollbar = (ScrollView) view.findViewById(R.id.scrollbar);
        mRecommend_gv = (GridView) view.findViewById(R.id.recommend_gv);//猜你喜欢
        mRecommandAdapter = new RecommandAdapter(getActivity());
        mRecommend_gv.setAdapter(mRecommandAdapter);
        mRecommend_gv.setOnItemClickListener(this);
    }

    /**
     * 修改广告栏的指示器，跟随图片切换
     *
     * @param position
     */
    private void changeAdindicator(int position) {
        //获取容器内部子控件的个数
        int childCount = mAdIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mAdIndicator.getChildAt(i).setSelected(i == position);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        long pid = mRecommandAdapter.getItemId(position);
        intent.putExtra(ProductDetailsActivity.PID_KEY, pid);
        startActivity(intent);
    }
}
