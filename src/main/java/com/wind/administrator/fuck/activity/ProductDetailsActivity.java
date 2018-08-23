package com.wind.administrator.fuck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.adapter.ProductDetailsAdapter;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.ProductDetailsController;
import com.wind.administrator.fuck.listener.JDPagerChangeListener;

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {
    public static final String PID_KEY = "pid";//
    public long mProductId;    //商品id
    private View mDetailsView;
    private LinearLayout mDetailsLl;
    private View mIntroduceView;
    private LinearLayout mIntroduceLl;
    private View mCommentTv;
    private LinearLayout mCommentLl;
    private ImageView mMoreIv;
    private ViewPager mContainerVp;
    private TextView mAdd2shopcar;
    private ProductDetailsAdapter mProductDetailsAdapter;
    public String mProductVersion ; //要购买的型号
    public int mBuyCount ;//要购买的数量
    @Override
    protected void handleUI(Message msg) {
        if(msg.what == IDivMessage.ADD2SHOPCAR_ACTION){
            RResult result = (RResult) msg.obj;
            if(result.isSuccess()){
                Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Log.i("cdcck","++++++++++++++++++++++++++++++++购买失败");
                Toast.makeText(this, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initView();
        initData();
        initController();
    }

    @Override
    protected void initController() {
        mController = new ProductDetailsController(this);
        mController.setIModelChangeListener(this);

    }

    private void initData() {
        Intent intent = getIntent();
        mProductId = intent.getLongExtra(PID_KEY, 0);
        if (mProductId == 0) {
            Toast.makeText(this, "数据异常", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        mDetailsView = (View) findViewById(R.id.details_view);
        mDetailsLl = (LinearLayout) findViewById(R.id.details_ll);
        mIntroduceView = (View) findViewById(R.id.introduce_view);
        mIntroduceLl = (LinearLayout) findViewById(R.id.introduce_ll);
        mCommentTv = (View) findViewById(R.id.comment_tv);
        mCommentLl = (LinearLayout) findViewById(R.id.comment_ll);
        mMoreIv = (ImageView) findViewById(R.id.more_iv);

        mAdd2shopcar = (TextView) findViewById(R.id.add2shopcar);

        mAdd2shopcar.setOnClickListener(this);
        mDetailsLl.setOnClickListener(this);
        mIntroduceLl.setOnClickListener(this);
        mCommentLl.setOnClickListener(this);
        mContainerVp = (ViewPager) findViewById(R.id.container_vp);
        mProductDetailsAdapter = new ProductDetailsAdapter(getSupportFragmentManager());
        mContainerVp.setAdapter(mProductDetailsAdapter);
        mContainerVp.addOnPageChangeListener(new JDPagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeIndicator(mIntroduceView);
                        break;
                    case 1:
                        changeIndicator(mDetailsView);
                        break;
                    case 2:
                        changeIndicator(mCommentTv);
                        break;

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add2shopcar: //加入到购物车
                add2Shopcar();
                break;
            case R.id.introduce_ll://商品详情

                mContainerVp.setCurrentItem(0, true);//true代表切换时有动画效果
                break;
            case R.id.details_ll://商品详情

                mContainerVp.setCurrentItem(1, true);
                break;
            case R.id.comment_ll://商品评价

                mContainerVp.setCurrentItem(2, true);
                break;
        }
    }

    /**
     * 加入购物车点击事件
     */
    private void add2Shopcar() {
        //1.判断型号是否选中
        //2.判断数据是否大于0   两个操作都在ProductInfoFragment
        if(mProductVersion == null||mBuyCount == 0){
            Toast.makeText(this, "请选择要购买的类型和数量", Toast.LENGTH_SHORT).show();
            return;
        }
        //3.发送网络请求
        JDApplication application = (JDApplication) getApplication();
        Long userId = application.getUserId();
        mController.sendAsyncMessage(IDivMessage.ADD2SHOPCAR_ACTION,userId,mProductId,mBuyCount,mProductVersion);

    }

    /**
     * 修改顶部的指示器
     */
    private void changeIndicator(View view) {
        mIntroduceView.setVisibility(view == mIntroduceView ? View.VISIBLE : View.INVISIBLE);
        mDetailsView.setVisibility(view == mDetailsView ? View.VISIBLE : View.INVISIBLE);
        mCommentTv.setVisibility(view == mCommentTv ? View.VISIBLE : View.INVISIBLE);
    }
}
