package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class RProductList {
    private long id ;
    private String name ;
    private String iconUrl ;
    private double price;
    private int commentCount ;
    private int favcomRate ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
