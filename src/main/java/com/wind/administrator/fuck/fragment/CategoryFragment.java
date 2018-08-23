package com.wind.administrator.fuck.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.adapter.TopCategoryAdapter;
import com.wind.administrator.fuck.bean.RTopCateGory;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.CategoryController;
import com.wind.administrator.fuck.ui.SubCategoryView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18 0018.
 * 分类
 */

public class CategoryFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView mToptv;
    private TopCategoryAdapter mTopCategoryAdapter;
    private SubCategoryView mSubcategoryView;

    @Override
    protected void handleUI(Message msg) {
        if(msg.what == IDivMessage.TOP_CATEGORY_ACTION){
            List<RTopCateGory> datas = (List<RTopCateGory>) msg.obj;
            mTopCategoryAdapter.setDatas(datas);
            mTopCategoryAdapter.notifyDataSetChanged();
            //模拟用户点击了第一个item （默认展示第一栏的内容）
            mToptv.performItemClick(null,0,0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initViews();
        requestTopCategoryDatas();

    }

    private void requestTopCategoryDatas() {
        mController.sendAsyncMessage(IDivMessage.TOP_CATEGORY_ACTION);
    }

    private void initViews() {
        mToptv = (ListView) getActivity().findViewById(R.id.top_lv);
        mTopCategoryAdapter = new TopCategoryAdapter(getActivity());
        mToptv.setAdapter(mTopCategoryAdapter);
        mToptv.setOnItemClickListener(this);
        //右边的布局（View）
        mSubcategoryView = (SubCategoryView) getActivity().findViewById(R.id.subcategory);
    }

    @Override
    protected void initController() {
        mController = new CategoryController(getActivity());
        mController.setIModelChangeListener(this);
    }

    /**
     *  一级列表item点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TOP 1.要将当前点击的item索引告诉adapter
        mTopCategoryAdapter.mCurrentTapIndex=position;
        //TOP 2.刷新界面
        mTopCategoryAdapter.notifyDataSetChanged();
        //TOP 4.subCategoryView（二级）
            //（1）拿到点击的数据
        RTopCateGory bean = mTopCategoryAdapter.getItem(position);
            //（2）把item的数据传到右边
        mSubcategoryView.onShow(bean);
    }
}
