package com.wind.administrator.fuck.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wind.administrator.fuck.bean.RCommentCount;
import com.wind.administrator.fuck.bean.RComments;
import com.wind.administrator.fuck.bean.RGoodComment;
import com.wind.administrator.fuck.bean.RResult;
import com.wind.administrator.fuck.bean.SProductListParams;
import com.wind.administrator.fuck.cons.CommentType;
import com.wind.administrator.fuck.cons.IDivMessage;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class ProductDetailsController extends BaseController {

    public ProductDetailsController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object[] values) {
        switch (action) {
            case IDivMessage.PRODUCT_INFO_ACTION:
                mListener.onModelChanged(action, loadProductInfoDatas((Long) values[0]));
                break;
            case IDivMessage.PRODUCT_COMMENT_ACTION:
                mListener.onModelChanged(action, loadCommentCount((Long) values[0]));
                break;
            case IDivMessage.COMMENT_BY_TYPE_ACTION:
                mListener.onModelChanged(action, loadCommentByType((Long) values[0], (Integer) values[1]));
                break;
            case IDivMessage.GOOD_COMMENT_ACTION:
                mListener.onModelChanged(action, loadGoodComments((Long) values[0]));
                break;
            case IDivMessage.ADD2SHOPCAR_ACTION:
                RResult rResult = add2ShopCar((Long) values[0], (Long) values[1], (Integer) values[2], (String) values[3]);
                mListener.onModelChanged(action,rResult);
                break;
        }
    }

    private RResult add2ShopCar(Long userId, Long mProductId, int mBuyCount, String mProductVersion) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        params.put("productId", mProductId+"");
        params.put("buyCount", mBuyCount+"");
        params.put("pversion", mProductVersion+"");
        String jsonStr = NetworkUtil.doPost(NetworkConstant.TO_SHOPCAR_URL, params);

        return JSON.parseObject(jsonStr, RResult.class);

    }

    private List<RGoodComment> loadGoodComments(Long pid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("productId", pid + "");
        params.put("type", "1");//评论类型（1-好评 2-中评 3-差评）
        String jsonStr = NetworkUtil.doPost(NetworkConstant.GOOD_COMMENT_URL, params);

        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), RGoodComment.class);
        }
        return new ArrayList<>();


    }

    private List<RComments> loadCommentByType(long pid, int commentType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("productId", pid + "");
        if (commentType != CommentType.HAS_IMAGE_COMMENT) {
            params.put("type", commentType + "");
        } else {
            params.put("type", CommentType.ALL_COMMENT + "");
            params.put("hasImgCom", "true");
        }

        String jsonStr = NetworkUtil.doPost(NetworkConstant.COMMENT_DETAIL_URL, params);

        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseArray(resultBean.getResult(), RComments.class);
        }
        return new ArrayList<>();
    }

    /**
     * @param pid 传入商品ID
     * @return
     */
    private RCommentCount loadCommentCount(long pid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("productId", pid + "");
        String jsonStr = NetworkUtil.doPost(NetworkConstant.COMMENT_COUNT_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
        if (resultBean.isSuccess()) {
            return JSON.parseObject(resultBean.getResult(), RCommentCount.class);
        }
        return null;
    }

    /**
     * 因为商品加载的过程中有可能失败，所以如果没有获取到数据，就不需要显示（关闭）
     *
     * @param productId
     * @return RResult
     */
    private RResult loadProductInfoDatas(long productId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", productId + "");
        String jsonStr = NetworkUtil.doGet(NetworkConstant.PRODUCT_INFO_URL, params);
        return JSON.parseObject(jsonStr, RResult.class);

    }
}
