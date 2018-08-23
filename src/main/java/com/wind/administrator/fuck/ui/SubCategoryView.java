package com.wind.administrator.fuck.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.ProductListActivity;
import com.wind.administrator.fuck.bean.RSubCategory;
import com.wind.administrator.fuck.bean.RTopCateGory;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.controller.CategoryController;
import com.wind.administrator.fuck.listener.IModelChangeListener;
import com.wind.administrator.fuck.util.AsyncImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 * 一个ScrollView不能嵌套多个子控件，可以用LinearLayout，再往里添加
 */

public class SubCategoryView extends ScrollView implements IModelChangeListener {


    private LinearLayout mContainerLl;
    private RTopCateGory mTopCategoryBean;
    private CategoryController mController;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == IDivMessage.SUB_CATEGORY_ACTION) {
                List<RSubCategory> datas = (List<RSubCategory>) msg.obj;
                for (int i = 0; i < datas.size(); i++) {
                    //初始化二级分类的标题
                    RSubCategory secondCategory = datas.get(i);
                    initSecondCategoryTitleTv(secondCategory.getName());
                    //初始化三级分类的列表
                    initThreadCategories(secondCategory.getThirdCategory());
                }
            }
        }
    };
    private AsyncImageLoader mAsyncImageLoader;

    /**
     * 二级分类
     *
     * @param name
     */
    private void initSecondCategoryTitleTv(String name) {
        TextView secondTitleTv = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        secondTitleTv.setLayoutParams(params);
        secondTitleTv.setText(name);
        mContainerLl.addView(secondTitleTv);
    }

    /**
     * 三级分类
     *
     * @param thirdCategory
     */
    private static final int MAX_COUNTS = 3;//最多允许有3列

    private void initThreadCategories(String thirdCategory) {
        //三级分类跟一级分类相同
        List<RTopCateGory> datas = JSON.parseArray(thirdCategory, RTopCateGory.class);
        //计算出当前的行数，最多一行有三个，求余，有余数的话行数就= 商+1
        int totalSize = datas.size();
        int lineSize = totalSize / MAX_COUNTS;
        //有余数就加 1
        lineSize += totalSize % MAX_COUNTS != 0 ? 1 : 0;
        //计算列数
        //每个数据的索引 = 行的索引*MAX_COUNTS+该数据所咋列的索引
        for (int i = 0; i < lineSize; i++) {
            //创建一个行的容器
            LinearLayout lineContainerLl = initLineContainer();
            //第一列
            if (i * MAX_COUNTS + 0 < totalSize) {
                addColumns(datas.get(i * MAX_COUNTS + 0), lineContainerLl);
            }
            //第二列
            if (i * MAX_COUNTS + 1 < totalSize) {
                addColumns(datas.get(i * MAX_COUNTS + 1), lineContainerLl);
            }
            //第三列
            if (i * MAX_COUNTS + 2 < totalSize) {
                addColumns(datas.get(i * MAX_COUNTS + 2), lineContainerLl);
            }
        }
    }

    /**
     * 创建三级分类行的容器
     *
     * @return
     */
    private LinearLayout initLineContainer() {
        //1.创建一个行的容器
        LinearLayout lineContainer = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lineContainer.setLayoutParams(params);
        //2.添加到大的容器里面
        mContainerLl.addView(lineContainer);
        return lineContainer;
    }

    /**
     * @param bean            每一列界面需要的数据
     * @param lineContainerLl 该列所在行的容器
     */
    private void addColumns(final RTopCateGory bean, LinearLayout lineContainerLl) {

        //1.创建一个垂直的LinearLayout添加到行里面
        LinearLayout columnLl = new LinearLayout(getContext());
        LinearLayout.LayoutParams columnLlParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        columnLl.setLayoutParams(columnLlParams);//将宽高进行绑定
        columnLl.setOrientation(LinearLayout.VERTICAL);
        lineContainerLl.addView(columnLl);
        //2.创建一个图片控件 添加到LinearLayout中
        ImageView iv = new ImageView(getContext());
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(getWidth() / 3, getHeight() / 3);
        iv.setLayoutParams(ivParams);//将宽高进行绑定
        mAsyncImageLoader.displayImage(NetworkConstant.BASE_URL + bean.getBannerUrl(), iv);
        columnLl.addView(iv);

        //3.创建一个文本控件 添加到LinearLayout中
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(getWidth() / 3, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(tvParams);//将宽高进行绑定
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setText(bean.getName());
        columnLl.addView(tv);
        //4.给列的容器添加点击事件
        columnLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductListActivity.class);
                //品牌列表接口请求需要一级分类的id
                intent.putExtra(ProductListActivity.THIRD_CATEGORY_KEY,bean.getId());
                //传递三级分类的id
                intent.putExtra(ProductListActivity.TOP_CATEGORY_KEY,mTopCategoryBean.getId());
                getContext().startActivity(intent);
            }
        });
    }

    public SubCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContainerLl = (LinearLayout) findViewById(R.id.child_container_ll);
        initAysncImageLoader();
        initController();

    }

    private void initAysncImageLoader() {
        mAsyncImageLoader = AsyncImageLoader.getInstance(getContext());
    }

    private void initController() {
        mController = new CategoryController(getContext());
        mController.setIModelChangeListener(this);
    }

    /**
     * 每次点击的时候调用
     *
     * @param bean
     */
    public void onShow(RTopCateGory bean) {
        mTopCategoryBean = bean;
        mContainerLl.removeAllViews();//清除之前的
        initTopBanner();
        requestSubCategoryDatas();


    }

    private void requestSubCategoryDatas() {
        //获取到一级分类的ID,找到2级分类的数据
        long topId = mTopCategoryBean.getId();
        mController.sendAsyncMessage(IDivMessage.SUB_CATEGORY_ACTION, topId);

    }

    /**
     * 显示右边顶部的图片（广告栏）
     */
    private void initTopBanner() {
        String bannerUrl = NetworkConstant.BASE_URL + mTopCategoryBean.getBannerUrl();
        ImageView bannerIv = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
        bannerIv.setLayoutParams(params);
        bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
        mAsyncImageLoader.displayImage(bannerUrl, bannerIv);

        mContainerLl.addView(bannerIv);
    }

    @Override
    public void onModelChanged(int action, Object resultBean) {
        mHandler.obtainMessage(action, resultBean).sendToTarget();
    }
}
