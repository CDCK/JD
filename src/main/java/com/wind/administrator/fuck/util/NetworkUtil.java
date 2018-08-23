package com.wind.administrator.fuck.util;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15 0015.
 * 实现网络请求的方式：HttpUrlConnection/HttpClient/VolleyokHttp...
 */

public class NetworkUtil {
    /**
     * 通过HttpURLConnection实现一个get请求
     *
     * @param urlStr
     * @param params
     * @return
     */
    public static String doGet(String urlStr, HashMap<String, String> params) {
        try {
            String paramsStr = parseParams(params);
            //拿到Connection对象
            URL url = new URL(urlStr + "?" + paramsStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置GET请求方式
            conn.setRequestMethod("GET");
            //连接
//            conn.connect();
            //判断响应码
            if (conn.getResponseCode() == 200) {
                //得到响应流
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                return reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 通过HttpURLConnection实现一个post请求
     *
     * @param urlStr
     * @param params
     * @return
     */
    public static String doPost(String urlStr, HashMap<String, String> params) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置POST请求
            conn.setRequestMethod("POST");
            //向服务器写数据（）写出去  params--->转换成paramsStr
            String paramsStr = parseParams(params);
            //因为开关默认是关闭的，所以要先设置启动开关
            conn.setDoOutput(true);//允许写出
            conn.getOutputStream().write(paramsStr.getBytes());
            if (conn.getResponseCode() == 200) {
                //判断请求是否成功
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                return reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param params
     * @return
     */
    @NonNull
    private static String parseParams(HashMap<String, String> params) {
        String paramsStr = "";
        if (params == null || params.isEmpty()) {
            return "";
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsStr += entry.getKey() + "=" + entry.getValue() + "&";
        }
        paramsStr.substring(0, paramsStr.length() - 1);
        return paramsStr;
    }


}
