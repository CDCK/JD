package com.wind.administrator.fuck.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.AddAddressActivity;
import com.wind.administrator.fuck.adapter.AreaAdapter;
import com.wind.administrator.fuck.bean.RArea;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.BaseController;
import com.wind.administrator.fuck.controller.SettleController;
import com.wind.administrator.fuck.listener.IAreaChangeListener;
import com.wind.administrator.fuck.listener.IModelChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 * 选择区域弹出框
 */

public class ChooseAreaPop extends BasePopupWindow implements IModelChangeListener, AdapterView.OnItemClickListener {

    private ListView province_lv;
    private ListView city_lv;
    private ListView dist_lv;
    private BaseController mController;
    private AreaAdapter mProvinceAdapter;//省份
    private AreaAdapter mDistAdapter;//地区
    private AreaAdapter mCityAdapter;//城市
    private TextView submit_tv;//popupWindow中的确定按钮
    private IAreaChangeListener mListener;

    ///用来显示用户选择的城市
    private TextView tip_tv;
    private RArea mProvince;
    private RArea mCity;
    private RArea mDist;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == IDivMessage.PROVINCE_ACTION) {
                loadProvince((List<RArea>) msg.obj);
            } else if (msg.what == IDivMessage.CITY_ACTION) {
                loadCity((List<RArea>) msg.obj);
            } else if (msg.what == IDivMessage.DIST_ACTION) {
                loadDist((List<RArea>) msg.obj);
            }
        }
    };


    /**
     * 加载地区列表
     *
     * @param datas 传过来的地区数据
     */
    private void loadDist(List<RArea> datas) {
        Log.i("mylog", "loadDist: 加载地区列表");
        mDistAdapter.setDatas(datas);
        mDistAdapter.notifyDataSetChanged();
    }

    /**
     * 加载城市列表
     *
     * @param datas 传过来的城市数据
     */
    private void loadCity(List<RArea> datas) {
        Log.i("mylog", "loadCity: 加载城市列表");
        mCityAdapter.setDatas(datas);
        mCityAdapter.notifyDataSetChanged();
    }

    /**
     * 加载省级列表
     *
     * @param datas 传过来的RArea对象
     */
    private void loadProvince(List<RArea> datas) {
        //需要三个Adapter，因为三个类型一样，所以只需要一个
        Log.i("mylog", "loadProvince: 加载省级列表");
        mProvinceAdapter.setDatas(datas);
        mProvinceAdapter.notifyDataSetChanged();

    }

    public ChooseAreaPop(Context context) {
        mContext = context;
        initController();
        initViews();
    }

    private void initController() {
        mController = new SettleController(mContext);
        mController.setIModelChangeListener(this);
    }


    @Override
    protected void initViews() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.address_pop_view, null);
        //对三个列表进行Adapter绑定
        initAreaAdapter(contentView);

        //发送省市区异步请求,默认显示省级的列表
        mController.sendAsyncMessage(IDivMessage.PROVINCE_ACTION);

        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //1.让mPopupWindow的内部控件获取焦点
        mPopupWindow.setFocusable(true);
        //2.当内部控件可以获取焦点后，外部就不能获取焦点了（）
        //必须设置背景才有效
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //3.更新当前mPopupWindow的状态（告诉pop属性已经设置了）
        mPopupWindow.update();
    }

    /**
     * 对省市区三个列表进行Adapter绑定
     */
    private void initAreaAdapter(View contentView) {
        //获取省市区三个列表//创建Adapter进行绑定
        province_lv = (ListView) contentView.findViewById(R.id.province_lv);
        mProvinceAdapter = new AreaAdapter(mContext);
        province_lv.setAdapter(mProvinceAdapter);
        province_lv.setOnItemClickListener(this);
        city_lv = (ListView) contentView.findViewById(R.id.city_lv);
        mCityAdapter = new AreaAdapter(mContext);
        city_lv.setAdapter(mCityAdapter);
        city_lv.setOnItemClickListener(this);
        dist_lv = (ListView) contentView.findViewById(R.id.dist_lv);
        mDistAdapter = new AreaAdapter(mContext);
        dist_lv.setAdapter(mDistAdapter);
        dist_lv.setOnItemClickListener(this);
        //用来提示用户所选择的城市
        tip_tv = (TextView) contentView.findViewById(R.id.tip_tv);
        //弹出框的选择城市的确定按钮
        submit_tv = (TextView) contentView.findViewById(R.id.submit_tv);
        // TODO: 2017/6/16 0016 确定按钮监听事件
        submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前的省市区是否选中
//                if (TextUtils.isEmpty(tip_tv.getText().toString())) {
                if(mProvince==null||mCity==null||mDist==null){
                    Toast.makeText(mContext, "请选择详细的地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                //修改对应的文本（choose_province_tv）
               if(mListener!=null){
                   mListener.setAreaChange(mProvince,mCity,mDist);
               }
                //隐藏popupWindow
//                mPopupWindow.dismiss();
                onDismiss();
            }
        });
    }

    /**
     * 使PopupWindow显示出来
     * @param anchorView
     */
    @Override
    public void onShow(View anchorView) {
        if (!mPopupWindow.isShowing()) {
            //显示区域：整个父控件
            mPopupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        }
    }


    @Override
    public void onModelChanged(int action, Object resultBean) {
        //相当于Handler一个消息
        mHandler.obtainMessage(action, resultBean).sendToTarget();
    }

    /**
     * 列表item的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int id1 = parent.getId();
        if (id1 == R.id.province_lv) {
            mProvince = (RArea) mProvinceAdapter.getItem(position);
            String code = mProvince.getCode();
            mController.sendAsyncMessage(IDivMessage.CITY_ACTION, code);
            //每次点击省份时，相应的地区列表要清空
            //先将mCity和mDist置为空
            mCity = null;
            mDist = null;
            showTip();
            loadDist(new ArrayList<RArea>());
        } else if (id1 == R.id.city_lv) {
            mCity = (RArea) mCityAdapter.getItem(position);
            String code = mCity.getCode();
            mController.sendAsyncMessage(IDivMessage.DIST_ACTION, code);
            //将mDist置为空
            mDist = null;
            showTip();
        } else if (id1 == R.id.dist_lv) {
            mDist = (RArea) mDistAdapter.getItem(position);
            showTip();
        }

    }

    /**
     * 显示提示文本，提示用户所选择的省市区
     */
    private void showTip() {
        String tipMessage = "";
        if (mProvince != null) {
            tipMessage += mProvince.getName();
        }
        if (mCity != null) {
            tipMessage += "/" + mCity.getName();
        }
        if (mDist != null) {
            tipMessage += "/" + mDist.getName();
        }
        tip_tv.setText(tipMessage);
    }

    public void setListener(IAreaChangeListener listener) {
        mListener = listener;
    }
}
