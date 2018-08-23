package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class RProductInfo {
    private long id;

    private String imgUrls;//图片地址的JSON
    private String typeList;//商品型号的JSON
    private String name;
    private double price;
    private String recomProduct;//推荐的商品
    private long recomProductId;//推荐的商品ID
    private boolean ifSaleOneself;//是否自营
    private int stockCount;//库存
    private int commentCount;//评论数
    private int favcomRate;//好评率

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getTypeList() {
        return typeList;
    }

    public void setTypeList(String typeList) {
        this.typeList = typeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRecomProduct() {
        return recomProduct;
    }

    public void setRecomProduct(String recomProduct) {
        this.recomProduct = recomProduct;
    }

    public long getRecomProductId() {
        return recomProductId;
    }

    public void setRecomProductId(long recomProductId) {
        this.recomProductId = recomProductId;
    }

    public boolean isIfSaleOneself() {
        return ifSaleOneself;
    }

    public void setIfSaleOneself(boolean ifSaleOneself) {
        this.ifSaleOneself = ifSaleOneself;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavcomRate() {
        return favcomRate;
    }

    public void setFavcomRate(int favcomRate) {
        this.favcomRate = favcomRate;
    }
}
