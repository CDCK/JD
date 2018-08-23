package com.wind.administrator.fuck.fragment;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.LoginActivity;
import com.wind.administrator.fuck.activity.OrderListActivity;
import com.wind.administrator.fuck.bean.RLoginUser;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.db.UserDao;
import com.wind.administrator.fuck.util.AsyncImageLoader;
import com.wind.administrator.fuck.util.NetworkUtil;

import static com.wind.administrator.fuck.cons.NetworkConstant.BASE_URL;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class MyJdFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mUserIconIv;
    private TextView mUserNameTv;
    private TextView mUserLevelTv;
    private TextView mWaitPayTv;
    private LinearLayout mWaitPayLl;
    private TextView mWaitReceiveTv;
    private LinearLayout mWaitReceiveLl;
    private LinearLayout mMimeOrder;
    private Button mLogoutBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myjd, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //拿到外部的MainActivity
        JDApplication application = (JDApplication) getActivity().getApplication();
        RLoginUser loginUser = application.mLoginUser;
        if(loginUser != null){
            //判断拿到的数据不为空
            mUserNameTv.setText(loginUser.getUserName());
//          mUserLevelTv.setText(loginUser.getUserLevel()+"");
            mUserLevelTv.setText(loginUser.getUserLevelStr());
            mWaitPayTv.setText(loginUser.getWaitPayCount()+"");
            mWaitReceiveTv.setText(loginUser.getWaitReceiveCount()+"");

            /*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).build();
            //做了一个初始化，配置了ImageLoaderConfiguration
            ImageLoader instance = ImageLoader.getInstance();
            instance.init(config);
            String userIcon = loginUser.getUserIcon();//获取的知识子路径
            String url = NetworkConstant.BASE_URL+userIcon;
            instance.displayImage(url,mUserIconIv);*/
            String userIcon = loginUser.getUserIcon();//获取的知识子路径
            String url = NetworkConstant.BASE_URL+userIcon;
            AsyncImageLoader.getInstance(getActivity()).displayImage(url,mUserIconIv);
        }

    }

    private void initView(View view) {
        mUserIconIv = (ImageView) view.findViewById(R.id.user_icon_iv);
        mUserNameTv = (TextView) view.findViewById(R.id.user_name_tv);
        mUserLevelTv = (TextView) view.findViewById(R.id.user_level_tv);
        mWaitPayTv = (TextView) view.findViewById(R.id.wait_pay_tv);
        mWaitPayLl = (LinearLayout) view.findViewById(R.id.wait_pay_ll);
        mWaitReceiveTv = (TextView) view.findViewById(R.id.wait_receive_tv);
        mWaitReceiveLl = (LinearLayout) view.findViewById(R.id.wait_receive_ll);
        mMimeOrder = (LinearLayout) view.findViewById(R.id.mime_order);
        mMimeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/6/21 0021 跳到订单列表页面
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });
        mLogoutBtn = (Button) view.findViewById(R.id.logout_btn);

        mWaitPayTv.setOnClickListener(this);
        mWaitReceiveTv.setOnClickListener(this);
        mLogoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wait_pay_tv:

                break;
            case R.id.wait_receive_tv:

                break;
            case R.id.logout_btn:
                //清除账号密码
                clearAccount();
                //跳转到登录界面
                startLoginActivity();
                break;
        }
    }

    private void clearAccount() {
        new UserDao().clearAll();
    }

    private void startLoginActivity() {
        Intent intent =new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
