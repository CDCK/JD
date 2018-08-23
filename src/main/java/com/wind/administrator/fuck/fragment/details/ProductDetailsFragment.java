package com.wind.administrator.fuck.fragment.details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import com.wind.administrator.fuck.R;
import com.wind.administrator.fuck.activity.ProductDetailsActivity;
import com.wind.administrator.fuck.cons.NetworkConstant;
import com.wind.administrator.fuck.fragment.BaseFragment;

/**
 * Created by xmg on 2017/5/15.
 * 商品详情
 */
public class ProductDetailsFragment extends BaseFragment {

    private WebView mWbView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        mWbView.loadUrl(NetworkConstant.PRODUCT_DETAIL_URL+"?productId"+activity.mProductId);

    }

    private void initView(View view) {
        mWbView = (WebView) view.findViewById(R.id.wbView);

    }
}
