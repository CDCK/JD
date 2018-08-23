package com.wind.administrator.fuck.fragment.details;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.ProductDetailsActivity;
import com.wind.administrator.fuck.adapter.CommentsAdapter;
import com.wind.administrator.fuck.bean.RCommentCount;
import com.wind.administrator.fuck.bean.RComments;
import com.wind.administrator.fuck.cons.CommentType;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.controller.ProductDetailsController;
import com.wind.administrator.fuck.fragment.BaseFragment;

import java.util.List;


/**
 * Created by xmg on 2017/5/15.
 * 商品评论
 */
public class ProductCommentFragment extends BaseFragment implements View.OnClickListener {

    private TextView mAllCommentTip;
    private TextView mAllCommentTv;
    private LinearLayout mAllCommentLl;
    private TextView mPositiveCommentTip;
    private TextView mPositiveCommentTv;
    private LinearLayout mPositiveCommentLl;
    private TextView mCenterCommentTip;
    private TextView mCenterCommentTv;
    private LinearLayout mCenterCommentLl;
    private TextView mNagetiveCommentTip;
    private TextView mNagetiveCommentTv;
    private LinearLayout mNagetiveCommentLl;
    private TextView mHasImageCommentTip;
    private TextView mHasImageCommentTv;
    private LinearLayout mHasImageCommentLl;
    private ListView mCommentLv;
    private long mProductId;
    private CommentsAdapter mCommentsAdapter;

    @Override
    protected void handleUI(Message msg) {
        if(msg.what == IDivMessage.PRODUCT_COMMENT_ACTION){
            loadCommentCount((RCommentCount) msg.obj);
        }else if(msg.what == IDivMessage.COMMENT_BY_TYPE_ACTION){
            loadCommentByType((List<RComments>) msg.obj);
        }
    }
    private void loadCommentByType(List<RComments> datas){
            mCommentsAdapter.setDatas(datas);
            mCommentsAdapter.notifyDataSetChanged();
    }
    private void loadCommentCount(RCommentCount bean){
        if(bean != null){
            mAllCommentTv.setText(bean.getAllComment()+"");
            mPositiveCommentTv.setText(bean.getPositiveCom()+"");
            mCenterCommentTv.setText(bean.getModerateCom()+"");
            mNagetiveCommentTv.setText(bean.getNegativeCom()+"");
            mHasImageCommentTv.setText(bean.getHasImgCom()+"");

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_comment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initController();
        requestCommentCountDatas();
        requestAllCommentDatas();

    }

    /**
     * 默认显示全部评论
     */
    private void requestAllCommentDatas() {
        getActivity().findViewById(R.id.all_comment_ll).performClick();
    }

    private void initData() {
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        mProductId = activity.mProductId;
    }

    private void requestCommentCountDatas() {

        mController.sendAsyncMessage(IDivMessage.PRODUCT_COMMENT_ACTION,mProductId);

    }

    @Override
    protected void initController() {
        mController = new ProductDetailsController(getActivity());
        mController.setIModelChangeListener(this);
    }

    private void initView(View view) {
        mAllCommentTip = (TextView) view.findViewById(R.id.all_comment_tip);
        mAllCommentTv = (TextView) view.findViewById(R.id.all_comment_tv);
        mAllCommentLl = (LinearLayout) view.findViewById(R.id.all_comment_ll);
        mPositiveCommentTip = (TextView) view.findViewById(R.id.positive_comment_tip);
        mPositiveCommentTv = (TextView) view.findViewById(R.id.positive_comment_tv);
        mPositiveCommentLl = (LinearLayout) view.findViewById(R.id.positive_comment_ll);
        mCenterCommentTip = (TextView) view.findViewById(R.id.center_comment_tip);
        mCenterCommentTv = (TextView) view.findViewById(R.id.center_comment_tv);
        mCenterCommentLl = (LinearLayout) view.findViewById(R.id.center_comment_ll);
        mNagetiveCommentTip = (TextView) view.findViewById(R.id.nagetive_comment_tip);
        mNagetiveCommentTv = (TextView) view.findViewById(R.id.nagetive_comment_tv);
        mNagetiveCommentLl = (LinearLayout) view.findViewById(R.id.nagetive_comment_ll);
        mHasImageCommentTip = (TextView) view.findViewById(R.id.has_image_comment_tip);
        mHasImageCommentTv = (TextView) view.findViewById(R.id.has_image_comment_tv);
        mHasImageCommentLl = (LinearLayout) view.findViewById(R.id.has_image_comment_ll);

        mAllCommentLl.setOnClickListener(this);
        mPositiveCommentLl.setOnClickListener(this);
        mCenterCommentLl.setOnClickListener(this);
        mNagetiveCommentLl.setOnClickListener(this);
        mHasImageCommentLl.setOnClickListener(this);
        mCommentLv = (ListView) view.findViewById(R.id.lv);
        mCommentsAdapter = new CommentsAdapter(getActivity());
        mCommentLv.setAdapter(mCommentsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_comment_ll:
                mController.sendAsyncMessage(IDivMessage.COMMENT_BY_TYPE_ACTION,mProductId, CommentType.ALL_COMMENT);
                break;
            case R.id.positive_comment_ll:
                mController.sendAsyncMessage(IDivMessage.COMMENT_BY_TYPE_ACTION,mProductId,  CommentType.POST_COMMENT);
                break;
            case R.id.center_comment_ll:
                mController.sendAsyncMessage(IDivMessage.COMMENT_BY_TYPE_ACTION, mProductId, CommentType.MODE_COMMENT);
                break;
            case R.id.nagetive_comment_ll:
                mController.sendAsyncMessage(IDivMessage.COMMENT_BY_TYPE_ACTION,mProductId,  CommentType.NAGE_COMMENT);
                break;
            case R.id.has_image_comment_ll:
                mController.sendAsyncMessage(IDivMessage.COMMENT_BY_TYPE_ACTION,mProductId,  CommentType.HAS_IMAGE_COMMENT);
                break;
        }
    }
}
