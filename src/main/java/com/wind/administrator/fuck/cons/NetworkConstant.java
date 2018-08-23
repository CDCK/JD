package com.wind.administrator.fuck.cons;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class NetworkConstant {


    public static final String BASE_URL = "http://mall.520it.com";
    //三种环境：开发、测试、正式环境，需要哪种环境就注释掉其它环境
    //public static final String BASE_URL = "http://test.520it.com";
    //方便管理
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String REGIST_URL = BASE_URL + "/regist";
    public static final String BANNER_URL = BASE_URL + "/banner";
    public static final String SECOND_KILL_URL = BASE_URL + "/seckill";
    public static final String GET_YOUFAV_URL = BASE_URL + "/getYourFav";
    public static final String CATE_GORY_URL = BASE_URL + "/category";
    public static final String BRAND_URL = BASE_URL + "/brand";
    public static final String PRODUCT_LIST_URL = BASE_URL + "/searchProduct";
    public static final String PRODUCT_INFO_URL = BASE_URL + "/productInfo";
    public static final String PRODUCT_DETAIL_URL = BASE_URL + "/productDetail";
    public static final String COMMENT_COUNT_URL = BASE_URL + "/commentCount";
    public static final String COMMENT_DETAIL_URL = BASE_URL + "/commentDetail";
    public static final String GOOD_COMMENT_URL = BASE_URL + "/productComment";
    public static final String TO_SHOPCAR_URL = BASE_URL + "/toShopCar";
    public static final String SHOP_CAR_URL = BASE_URL + "/shopCar";
    public static final String DEL_SHOPCAR_URL = BASE_URL + "/delShopCar";
    public static final String RECEIVE_ADDRESS_URL = BASE_URL + "/receiveAddress";
    public static final String DELADDRESS_URL = BASE_URL + "/delAddress";

    //省市区
    public static final String PROVINCE_URL = BASE_URL + "/province";
    public static final String CITY_URL = BASE_URL + "/city";
    public static final String AREA_URL = BASE_URL + "/area";

    public static final String ADD_RECEIER_URL = BASE_URL + "/addAddress";
    public static final String ADD_ORDER_URL = BASE_URL + "/addOrder";
    public static final String ORDER_LIST_URL = BASE_URL + "/getOrderByStatus";


    //
    public static final String GET_PAYINFO_URL = BASE_URL + "/getPayInfo";
    //模拟支付
    public static final String MOCK_PAY_URL = BASE_URL + "/pay";


}
