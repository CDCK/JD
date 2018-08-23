package com.wind.administrator.fuck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.bean.RLoginUser;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.LoginController;
import com.wind.administrator.fuck.db.Account;
import com.wind.administrator.fuck.db.UserDao;
import com.wind.administrator.fuck.listener.IModelChangeListener;
import com.wind.administrator.fuck.util.AESUtils;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class LoginActivity extends BaseActivity implements IModelChangeListener, View.OnClickListener {

    private TextView title_v;
    private EditText name_et;
    private EditText pwd_et;
    private Button login_btn;
    private String mName;
    private String mPwd;

    @Override
    protected void handleUI(Message msg) {
        if (msg.what == IDivMessage.LOGIN_ACTION) {
            RResult rresult = (RResult) msg.obj;
            Log.i("LOGIN", rresult + "");
            if (rresult.isSuccess()) {
                //弹出提示登录成功
                showTip("登录成功!");
                String result = rresult.getResult();
                //保存用户的登录信息到Application（相当于一个内存中）中
                saveLoginUser2Application(result);
                saveAccount2DB();
                startMainActivity();
            } else {
                //登录失败
                showTip(rresult.getErrorMsg());
            }
        }
    }

    /**
     * 保存账号密码到数据库
     */
    private void saveAccount2DB() {
        UserDao userDao = new UserDao();
        //先清除上一个登录的信息
        userDao.clearAll();
        Account account = new Account();
        String name = name_et.getText().toString().trim();
        String pwd = pwd_et.getText().toString().trim();
        try {
            pwd = AESUtils.encrypt(pwd);
            account.setPwd(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        account.setName(name);

        userDao.saveUser(account);
    }

    /**
     * 跳转到MainActivity界面
     */
    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginUser2Application(String result) {
        RLoginUser rLoginUser = JSON.parseObject(result, RLoginUser.class);
        JDApplication application = (JDApplication) getApplication();
        //将用户的信息保存到JDApplication中
        application.mLoginUser = rLoginUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initController();
        readAccount();
    }

    private void readAccount() {
        UserDao userDao = new UserDao();
        Account loginUser = userDao.getLoginUser();
        if (loginUser != null) {
            name_et.setText(loginUser.getName());
            //得到密文
            String decrypt = null;
            try {
                decrypt = AESUtils.decrypt(loginUser.getPwd());
                pwd_et.setText(decrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    protected void initController() {
        mController = new LoginController(this);
        mController.setIModelChangeListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_login);
        title_v = (TextView) findViewById(R.id.title_v);
        name_et = (EditText) findViewById(R.id.name_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        login_btn = (Button) findViewById(R.id.login_btn);
        //登录按钮点击事件
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn://登录的按钮
                //点击按钮就实现login()方法
                login();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        //获取到输入的账号和密码
        mName = name_et.getText().toString().trim();
        mPwd = pwd_et.getText().toString().trim();
        mController.sendAsyncMessage(IDivMessage.LOGIN_ACTION, mName, mPwd);
    }

    /**
     * 注册（新用户）按钮监听
     */
    public void registClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(intent);

    }
}
