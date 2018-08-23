package com.wind.administrator.fuck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.adapter.BrandAdapter;
import com.wind.administrator.fuck.adapter.ProductListAdapter;
import com.wind.administrator.fuck.bean.RBrand;
import com.wind.administrator.fuck.bean.RProductList;
import com.wind.administrator.fuck.bean.SProductListParams;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.ProductListController;
import com.wind.administrator.fuck.ui.pop.ProductSortPop;
import com.wind.administrator.fuck.util.FixedViewUtil;

import java.util.List;

import static com.wind.administrator.fuck.R.id.drawerlayout;
import static com.wind.administrator.fuck.R.id.maxPrice_et;
import static com.wind.administrator.fuck.R.id.minPrice_et;

/**
 * 点击三级分类id进入
 */
public class ProductListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String TOP_CATEGORY_KEY = "topCategoryId";
    public static final String THIRD_CATEGORY_KEY = "thirdCategoryId";
    private TextView mAllIndicator;
    private TextView mSaleIndicator;
    private TextView mPriceIndicator;
    private TextView mChooseIndicator;
    private ListView mProductLv;
    private LinearLayout mContentView;
    private TextView mJdTakeTv;
    private TextView mPaywhenreceiveTv;
    private TextView mJusthasstockTv;
    private EditText mMinPriceEt;
    private EditText mMaxPriceEt;
    private GridView mBrandGv;
    private ScrollView mSlideView;
    private DrawerLayout mDrawerlayout;
    private BrandAdapter mBrandAdapter;
    private long mTopcateGoryId;
    private long mThirdcateGoryId;
    private ProductSortPop mProductSortPop;
    private SProductListParams mSendParams;
    private ProductListAdapter mProductListAdapter;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what == IDivMessage.BRAND_ACTION) {
            showBrandGv((List<RBrand>) msg.obj);
        }else if(msg.what == IDivMessage.PRODUCT_LIST_ACTION){
            showProductList((List<RProductList>) msg.obj);
        }
    }
    private void showProductList(List<RProductList> datas){
        mProductListAdapter.setDatas(datas);
        mProductListAdapter.notifyDataSetChanged();
    }
    private void showBrandGv(List<RBrand> datas) {
        mBrandAdapter.setDatas(datas);
        mBrandAdapter.notifyDataSetChanged();
        FixedViewUtil.setGridViewHeightBasedOnChildren(mBrandGv, 3);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        initDatas();
        initController();
        initView();
        requestBrandDatas();
        requestProductList();
    }

    private void requestProductList() {
        mController.sendAsyncMessage(IDivMessage.PRODUCT_LIST_ACTION, mSendParams);
    }

    /**
     * 获取其它页面传递的数据
     */
    private void initDatas() {
        Intent intent = getIntent();
        mTopcateGoryId = intent.getLongExtra(TOP_CATEGORY_KEY, 0);
        mThirdcateGoryId = intent.getLongExtra(THIRD_CATEGORY_KEY, 0);

        if (mTopcateGoryId == 0 || mThirdcateGoryId == 0) {
            showTip("数据异常");
            finish();
        } else {
            mSendParams = new SProductListParams(mThirdcateGoryId);

        }

    }

    private void requestBrandDatas() {
        mController.sendAsyncMessage(IDivMessage.BRAND_ACTION, mTopcateGoryId);

    }

    @Override
    protected void initController() {
        mController = new ProductListController(this);
        mController.setIModelChangeListener(this);
    }

    private void initView() {
        mAllIndicator = (TextView) findViewById(R.id.all_indicator);
        mSaleIndicator = (TextView) findViewById(R.id.sale_indicator);
        mPriceIndicator = (TextView) findViewById(R.id.price_indicator);
        mChooseIndicator = (TextView) findViewById(R.id.choose_indicator);

        mContentView = (LinearLayout) findViewById(R.id.content_view);
        mJdTakeTv = (TextView) findViewById(R.id.jd_take_tv);
        mPaywhenreceiveTv = (TextView) findViewById(R.id.paywhenreceive_tv);
        mJusthasstockTv = (TextView) findViewById(R.id.justhasstock_tv);
        mMinPriceEt = (EditText) findViewById(minPrice_et);
        mMaxPriceEt = (EditText) findViewById(maxPrice_et);

        mSlideView = (ScrollView) findViewById(R.id.slide_view);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mAllIndicator.setOnClickListener(this);
        mSaleIndicator.setOnClickListener(this);
        mPriceIndicator.setOnClickListener(this);
        mChooseIndicator.setOnClickListener(this);

        mSlideView.setOnClickListener(this);
        //选择服务控件 京东配送、货到付款、仅看有货
        mJdTakeTv.setOnClickListener(this);
        mPaywhenreceiveTv.setOnClickListener(this);
        mJusthasstockTv.setOnClickListener(this);
        //品牌列表
        mBrandGv = (GridView) findViewById(R.id.brand_gv);
        mBrandAdapter = new BrandAdapter(this);
        mBrandGv.setAdapter(mBrandAdapter);
        mBrandGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBrandAdapter.mCurrentTapIndex = position;
                mBrandAdapter.notifyDataSetChanged();
            }
        });
        //商品列表
        mProductLv = (ListView) findViewById(R.id.product_lv);
        mProductListAdapter = new ProductListAdapter(this);
        mProductLv.setAdapter(mProductListAdapter);
        mProductLv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_indicator://综合
                if (mProductSortPop == null) {
                    mProductSortPop = new ProductSortPop(this, this);
                }
                mProductSortPop.onShow(v);

                break;
            case R.id.sale_indicator://销量
                mSendParams.sortType = SProductListParams.SORT_TYPE_SALE;
                requestProductList();
                break;
            case R.id.price_indicator://价格
                int sortType = mSendParams.sortType;
                if (sortType == SProductListParams.SORT_TYPE_DEFAULT ||
                        sortType == SProductListParams.SORT_TYPE_SALE ||
                        sortType == SProductListParams.SORT_TYPE_PRICE_DOWN) {
                    //当等于 0 1 3的时候，就改成 2
                    mSendParams.sortType = SProductListParams.SORT_TYPE_PRICE_UP;
                    requestProductList();
                } else if (sortType == SProductListParams.SORT_TYPE_PRICE_UP) {
                    //当等于 2 就改成 3
                    mSendParams.sortType = SProductListParams.SORT_TYPE_PRICE_DOWN;
                    requestProductList();
                }
                break;
            case R.id.choose_indicator:
                mDrawerlayout.openDrawer(mSlideView);
                break;
            case R.id.jd_take_tv:

            case R.id.paywhenreceive_tv:

            case R.id.justhasstock_tv:
                v.setSelected(!v.isSelected());
                break;
            case R.id.slide_view:

                break;
            case R.id.all_sort://综合排序
                mAllIndicator.setText("综合");
                mProductSortPop.onDismiss();
                mSendParams = new SProductListParams(mThirdcateGoryId);
                mSendParams.filterType = SProductListParams.FILTER_TYPE_ALL;
                requestProductList();
                break;
            case R.id.new_sort://新品排序
                mAllIndicator.setText("新品");
                mProductSortPop.onDismiss();
                mSendParams = new SProductListParams(mThirdcateGoryId);
                mSendParams.filterType = SProductListParams.FILTER_TYPE_NEW;
                requestProductList();
                break;
            case R.id.comment_sort://评价排序
                mAllIndicator.setText("评价");
                mProductSortPop.onDismiss();
                mSendParams = new SProductListParams(mThirdcateGoryId);
                mSendParams.filterType = SProductListParams.FILTER_TYPE_COMMENT;
                requestProductList();
                break;
        }
    }

    /**
     * 重置按钮监听
     * TODO
     *
     * @param view
     */
    public void resetClick(View view) {
        mJdTakeTv.setSelected(false);
        mPaywhenreceiveTv.setSelected(false);
        mJusthasstockTv.setSelected(false);
        mBrandAdapter.mCurrentTapIndex = -1;
        mBrandAdapter.notifyDataSetChanged();
        mSendParams = new SProductListParams(mThirdcateGoryId);
        requestProductList();
        //关闭页面
        mDrawerlayout.closeDrawer(mSlideView);
    }

    /**
     * 确定按钮
     * TODO
     *
     * @param view
     */
    public void chooseSearchClick(View view) {
        mSendParams = new SProductListParams(mThirdcateGoryId);
        //拿到选择服务的参数
        int deliverChoose = 0;
        if (mJdTakeTv.isSelected()) {
            deliverChoose += 1;
        }
        if (mPaywhenreceiveTv.isSelected()) {
            deliverChoose += 2;
        }
        if (mJusthasstockTv.isSelected()) {
            deliverChoose += 4;
        }
        mSendParams.deliverChoose = deliverChoose;
        //拿到品牌的id
        if (mBrandAdapter.mCurrentTapIndex != -1){
            long brandId = mBrandAdapter.getItemId(mBrandAdapter.mCurrentTapIndex);
            mSendParams.brandId = brandId ;
        }
        requestProductList();
        //关闭页面
        mDrawerlayout.closeDrawer(mSlideView);
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra(ProductDetailsActivity.PID_KEY,mProductListAdapter.getItemId(position));
        startActivity(intent);
    }
}
