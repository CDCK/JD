package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/5/21 0021.
 */

public class RSubCategory {
    private long id;
    private String name;
    private String thirdCategory;//三级分类的json

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

    public String getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(String thirdCategory) {
        this.thirdCategory = thirdCategory;
    }
}
