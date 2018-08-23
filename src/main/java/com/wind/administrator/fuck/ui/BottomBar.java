package com.wind.administrator.fuck.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.MainActivity;
import com.wind.administrator.fuck.listener.IBottomBarItemClickListener;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener {


    private ImageView mFragMainIv;
    private TextView mFragMain;
    private ImageView mFragCategoryIv;
    private TextView mFragCategory;
    private ImageView mFragShopcarIv;
    private TextView mFragShopcar;
    private ImageView mFragMineIv;
    private TextView mFragMine;
    private IBottomBarItemClickListener mListener;
    private int mCurrentTableId = -1;//记录bottomBar点击的View

    public BottomBar(Context context) {
        super(context);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 获取并赋值子控件
     */
    @Override
    protected void onFinishInflate() {
//        LayoutInflater.from().inflate();//将布局转换成View
        super.onFinishInflate();
        findViewById(R.id.frag_main_ll).setOnClickListener(this);
        findViewById(R.id.frag_category_ll).setOnClickListener(this);
        findViewById(R.id.frag_shopcar_ll).setOnClickListener(this);
        findViewById(R.id.frag_mine_ll).setOnClickListener(this);

        mFragMainIv = (ImageView) findViewById(R.id.frag_main_iv);
        mFragCategoryIv = (ImageView) findViewById(R.id.frag_category_iv);
        mFragShopcarIv = (ImageView) findViewById(R.id.frag_shopcar_iv);
        mFragMineIv = (ImageView) findViewById(R.id.frag_mine_iv);

        mFragMain = (TextView) findViewById(R.id.frag_main);
        mFragCategory = (TextView) findViewById(R.id.frag_category);
        mFragShopcar = (TextView) findViewById(R.id.frag_shopcar);
        mFragMine = (TextView) findViewById(R.id.frag_mine);
        //默认点击首页
        findViewById(R.id.frag_main_ll).performClick();
    }

    /**
     * 修改指示器
     *
     * @param resId
     */
    public void changeIndicators(int resId) {
        mFragMainIv.setSelected(resId == R.id.frag_main_ll);
        mFragMain.setSelected(resId == R.id.frag_main_ll);

        mFragCategoryIv.setSelected(resId == R.id.frag_category_ll);
        mFragCategory.setSelected(resId == R.id.frag_category_ll);

        mFragShopcarIv.setSelected(resId == R.id.frag_shopcar_ll);
        mFragShopcar.setSelected(resId == R.id.frag_shopcar_ll);

        mFragMineIv.setSelected(resId == R.id.frag_mine_ll);
        mFragMine.setSelected(resId == R.id.frag_mine_ll);
    }

    @Override
    public void onClick(View v) {
        //如果当前是已经点击了，就做一个拦截（连点两次）
        int mTabId = v.getId();
        if (mCurrentTableId == mTabId) {
            return;
        }
        switch (mTabId) {
            case R.id.frag_main_ll:

            case R.id.frag_category_ll:

            case R.id.frag_shopcar_ll:

            case R.id.frag_mine_ll:
                changeIndicators(mTabId);
                if (mListener != null) {
                    mListener.onItemClick(mTabId);
                }
                break;
        }
        //记录当前点击的View
        mCurrentTableId = mTabId;
    }

    public void setIBottomBarItemClickListener(IBottomBarItemClickListener listener) {
        this.mListener = listener;
    }
}
