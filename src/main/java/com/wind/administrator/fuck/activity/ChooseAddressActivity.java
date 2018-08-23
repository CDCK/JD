package com.wind.administrator.fuck.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wind.administrator.fuck.JDApplication;
import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.adapter.ReceiverAdapter;
import com.wind.administrator.fuck.bean.RReceiver;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.BaseController;
import com.wind.administrator.fuck.controller.SettleController;
import com.wind.administrator.fuck.listener.IDeleteReceiverListener;
import com.wind.administrator.fuck.ui.FlexiListView;

import java.util.List;

/**
 * 选择地址弹出的界面
 */
public class ChooseAddressActivity extends BaseActivity implements IDeleteReceiverListener, AdapterView.OnItemClickListener {

    private FlexiListView mLv;
    private ReceiverAdapter mReceiverAdapter;

    @Override
    protected void handleUI(Message msg) {
        switch (msg.what) {
            case IDivMessage.RECEIVER_LIST_ACTION:
                loadReceiverList((List<RReceiver>) msg.obj);
                break;
            case IDivMessage.RECEIVER_DELE_ACTION:
                loadReceiverDelete((RResult) msg.obj);
                break;
        }
    }

    private void loadReceiverDelete(RResult data) {
        if (data.isSuccess()) {
            showTip("删除成功");
            //刷新页面
            requestReceiverListData();
        } else {
            showTip(data.getErrorMsg());
        }
    }

    private void loadReceiverList(List<RReceiver> datas) {
        mReceiverAdapter.setDatas(datas);
        mReceiverAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        initView();
        initController();
        requestReceiverListData();

    }

    private void requestReceiverListData() {
        JDApplication application = (JDApplication) getApplication();
        mController.sendAsyncMessage(IDivMessage.RECEIVER_LIST_ACTION, application.getUserId(), false);
    }

    @Override
    protected void initController() {
        mController = new SettleController(this);
        mController.setIModelChangeListener(this);
    }

    private void initView() {
        mLv = (FlexiListView) findViewById(R.id.lv);
        mReceiverAdapter = new ReceiverAdapter(this);
        //删除某一个地址的事件添加
        mReceiverAdapter.setListener(this);
        mLv.setAdapter(mReceiverAdapter);
        mLv.setOnItemClickListener(this);
    }

    /**
     * 收货列表item的点击事件，当点击item时，将当前的bean数据返回
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RReceiver bean = (RReceiver) mReceiverAdapter.getItem(position);
        //传递数据
        Intent intent = new Intent();
        //传递对象，需要将RReceiver实现序列化
        intent.putExtra("RECEIVER",bean);
        setResult(0,intent);
finish();
    }

    /**
     *
     * @param receiverId 地址ID
     */
    @Override
    public void onReceiverDeleted(Long receiverId) {
        JDApplication application = (JDApplication) getApplication();
        Long userId = application.getUserId();
        Log.i("mylog", "onReceiverDeleted: >>>>>>>>>>>>>>>"+receiverId+"<<<<<<<<<<<<<<<<<<<"+userId);
        mController.sendAsyncMessage(IDivMessage.RECEIVER_DELE_ACTION, userId,receiverId);
    }



}
