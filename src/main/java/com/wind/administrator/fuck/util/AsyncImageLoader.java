package com.wind.administrator.fuck.util;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class AsyncImageLoader {
    private static AsyncImageLoader sInstance;
    private final ImageLoader mImageLoader;

    private AsyncImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        //做了一个初始化，配置了ImageLoaderConfiguration
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
    }

    public static AsyncImageLoader getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AsyncImageLoader(context);
        }
        return sInstance;
    }

    public void displayImage(String url, ImageView iv) {
        mImageLoader.displayImage(url, iv);
    }
}
