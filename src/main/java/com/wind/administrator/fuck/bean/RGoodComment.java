package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class RGoodComment {

    private int rate;//星星数
    private String userName;//评论用户名
    private String comment;//评论内容
    private String imgUrls;//评论图片路径

    private String time;//评论时间
    private int type;//评论类型



    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
