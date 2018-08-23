package com.wind.administrator.fuck.bean;

/**
 * Created by Administrator on 2017/6/16 0016.
 * 添加收货人地址参数
 */

public class RAddAddressParameter {
    public Long userId;
    public String name;
    public String phone;
    public String provinceCode;
    public String cityCode;
    public String distCode;
    public String addressDetails;
    public boolean isDefault;

    public RAddAddressParameter(Long userId, String name, String phone, String provinceCode, String cityCode, String distCode, String addressDetails, boolean isDefault) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.distCode = distCode;
        this.addressDetails = addressDetails;
        this.isDefault = isDefault;
    }

    public RAddAddressParameter() {

    }

}
