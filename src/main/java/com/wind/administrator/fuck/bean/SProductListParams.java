package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class SProductListParams {
    public long categoryId ;//三级分类Id
    public int filterType = FILTER_TYPE_ALL;//排序类型（1-综合 2-新品 3-评价）
    public int sortType;//排序条件（1-销量 2-价格高到低 3-价格低到高）
    public int deliverChoose;//选择类型（0-代表无选择 1代表京东配送 2-代表货到付款 4-代表仅看有货 3代表条件1+2 5代表条件1+4 6代表条件2+4）
    public long brandId = -1;//品牌id

    public static final int FILTER_TYPE_ALL = 1;
    public static final int FILTER_TYPE_NEW = 2;
    public static final int FILTER_TYPE_COMMENT = 3;

    public static final int SORT_TYPE_DEFAULT = 0;
    public static final int SORT_TYPE_SALE = 1;
    public static final int SORT_TYPE_PRICE_UP = 2;
    public static final int SORT_TYPE_PRICE_DOWN = 3;
    public SProductListParams(long categoryId) {
        this.categoryId = categoryId;

    }
}
