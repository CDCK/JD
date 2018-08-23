package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class RCommentCount {
    private int allComment ;//全部评论数
    private int positiveCom;//好评数
    private int moderateCom;//中评数
    private int negativeCom;//差评数
    private int hasImgCom;//有图评论数

    public int getAllComment() {
        return allComment;
    }

    public void setAllComment(int allComment) {
        this.allComment = allComment;
    }

    public int getPositiveCom() {
        return positiveCom;
    }

    public void setPositiveCom(int positiveCom) {
        this.positiveCom = positiveCom;
    }

    public int getModerateCom() {
        return moderateCom;
    }

    public void setModerateCom(int moderateCom) {
        this.moderateCom = moderateCom;
    }

    public int getNegativeCom() {
        return negativeCom;
    }

    public void setNegativeCom(int negativeCom) {
        this.negativeCom = negativeCom;
    }

    public int getHasImgCom() {
        return hasImgCom;
    }

    public void setHasImgCom(int hasImgCom) {
        this.hasImgCom = hasImgCom;
    }
}
