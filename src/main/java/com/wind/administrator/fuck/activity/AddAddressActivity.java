package com.wind.administrator.fuck.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RAddAddressParameter;
import com.wind.administrator.fuck.bean.RArea;
import com.wind.administrator.fuck.bean.RReceiver;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.SettleController;
import com.wind.administrator.fuck.listener.IAreaChangeListener;
import com.wind.administrator.fuck.ui.pop.ChooseAreaPop;

import org.json.JSONObject;

/**
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity implements IAreaChangeListener {

    private EditText mNameEt;
    private EditText mPhoneEt;
    private TextView mChooseProvinceTv;
    private EditText mAddressDetailsEt;
    private CheckBox mDefaultCbx;
    private LinearLayout mParentView;
    private ChooseAreaPop mChooseAreaPop;
    private IAreaChangeListener mListener;
    private String mProvinceCode;
    private String mCityCode;
    private String mAreaCode;

    @Override
    protected void handleUI(Message msg) {
        if(msg.what == IDivMessage.ADD_RECEIVER_ACTION){
            //设置数据
            RResult result = (RResult) msg.obj;
            if(result.isSuccess()){
                showTip("添加地址成功");
                RReceiver receiver = JSON.parseObject(result.getResult(), RReceiver.class);
                Intent intent = new Intent();
                intent.putExtra("RECEIVER",receiver);
                setResult(0,intent);
                finish();
            }else {
                showTip(result.getErrorMsg());
            }

        }
    }
    private void setText(){
        // TODO: 2017/6/16 0016  
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
        initController();
    }

    /**
     * 点击保存时，要实现网络请求,需要Controller
     */
    @Override
    protected void initController() {
        mController = new SettleController(this);
        mController.setIModelChangeListener(this);
    }

    private void initView() {
        mNameEt = (EditText) findViewById(R.id.name_et);
        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        //点击省市区，弹出popupWindow
        mChooseProvinceTv = (TextView) findViewById(R.id.choose_province_tv);
        mAddressDetailsEt = (EditText) findViewById(R.id.address_details_et);
        mDefaultCbx = (CheckBox) findViewById(R.id.default_cbx);
        //整个容器的View
        mParentView = (LinearLayout) findViewById(R.id.parent_view);

    }

    /**
     * 点击省市区,弹出相应的PopupWindow
     * @param view
     */
    public void reGetAddress(View view){
        //弹出popupWindow,不要每次都创建

        if(mChooseAreaPop==null) {
            Log.i("mylog", "reGetAddress: >>>>>>>>>>>>>>>>>>>>>>>>>点击省市区弹出PopupWindow");
            mChooseAreaPop = new ChooseAreaPop(this);
            //设置一个IAreaChangeListener
            mChooseAreaPop.setListener(this);
            mChooseAreaPop.onShow(mParentView);
        }else {
            Log.i("mylog", "reGetAddress: 当mChooseAreaPop不为null");
            mChooseAreaPop.onShow(mParentView);
        }
    }

    @Override
    public void onModelChanged(int action, Object resultBean) {
        super.onModelChanged(action, resultBean);
    }

    /**
     * 接口回调：改变省市区的文本
     * @param province
     * @param city
     * @param area
     */
    @Override
    public void setAreaChange(RArea province, RArea city, RArea area) {
        mChooseProvinceTv.setText(province.getName()+city.getName()+area.getName());
        mProvinceCode = province.getCode();
        mCityCode = city.getCode();
        mAreaCode = area.getCode();
    }

    /**
     * 点击保存按钮
     * @param view
     */
    public void saveAddress(View view){
        //先要检验，各个输入框和地址控件不为null
        if(mProvinceCode==null||mCityCode==null||mAreaCode==null
                || TextUtils.isEmpty(mNameEt.getText().toString())
                ||TextUtils.isEmpty(mPhoneEt.getText().toString())
                ||TextUtils.isEmpty(mAddressDetailsEt.getText().toString())){
            Toast.makeText(this, "请完整的输入信息", Toast.LENGTH_SHORT).show();
            return;
        }
        //发送一个异步请求操作,并把请求码参数传过去
        JDApplication app = (JDApplication) getApplication();
        String name = mNameEt.getText().toString();
        String phone = mPhoneEt.getText().toString();
        String address = mAddressDetailsEt.getText().toString();
        boolean isDefault = mDefaultCbx.isChecked();
        RAddAddressParameter params = new RAddAddressParameter(app.getUserId(),name,phone,
                mProvinceCode,mCityCode,mAreaCode,address,isDefault);
        mController.sendAsyncMessage(IDivMessage.ADD_RECEIVER_ACTION,params);


    }
}
