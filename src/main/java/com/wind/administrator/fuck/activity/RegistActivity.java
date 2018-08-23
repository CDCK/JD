package com.wind.administrator.fuck.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.RegistController;

public class RegistActivity extends BaseActivity {

    private TextView mTitleV;
    private EditText mUsernameEt;
    private EditText mPwdEt;
    private EditText mSurepwdEt;

    @Override
    protected void handleUI(Message msg) {
        if(msg.what == IDivMessage.REGIST_ACTION){
            RResult result = (RResult) msg.obj;
            if(result.isSuccess()){
                //注册成功
                showTip("注册成功");
                finish();
            }else {
                //注册失败的原因
                showTip(result.getErrorMsg());
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initController();
    }

    @Override
    protected void initController() {
        //主要是做网络请求
        mController = new RegistController(this);
        //每次网咯请求都要返回数据
        mController.setIModelChangeListener(this);

    }

    private void initView() {
        mTitleV = (TextView) findViewById(R.id.title_v);
        mUsernameEt = (EditText) findViewById(R.id.username_et);
        mPwdEt = (EditText) findViewById(R.id.pwd_et);
        mSurepwdEt = (EditText) findViewById(R.id.surepwd_et);
    }

    public void registClick(View v) {
        String name = mUsernameEt.getText().toString().trim();
        String pwd = mPwdEt.getText().toString().trim();
        String surepwd = mSurepwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(surepwd)) {
            showTip("请输入完整的信息");
            return;
        }
        //判断密码是否相等
        mController.sendAsyncMessage(IDivMessage.REGIST_ACTION,name,pwd);
    }

}
