package com.wz.swipe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangzhenguo on 2017/6/13.
 * 刷卡传递参数
 */

public class SwipeParam implements Parcelable {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 8宝机号
     */
    private String bbCode;
    /**
     * 商户号
     */
    private String mercId;
    /**
     * key
     */
    private String key;
    /**
     * 订单金额
     */
    private String amount;
    /**
     * 订单描述
     */
    private String remark;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBbCode() {
        return bbCode;
    }

    public void setBbCode(String bbCode) {
        this.bbCode = bbCode;
    }

    public String getMercId() {
        return mercId;
    }

    public void setMercId(String mercId) {
        this.mercId = mercId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.bbCode);
        dest.writeString(this.mercId);
        dest.writeString(this.key);
        dest.writeString(this.amount);
        dest.writeString(this.remark);
    }

    public void readFromParcel(Parcel source){
        orderId = source.readString();
        bbCode = source.readString();
        mercId = source.readString();
        key = source.readString();
        amount = source.readString();
        remark = source.readString();
    }

    public SwipeParam() {
    }

    protected SwipeParam(Parcel in) {
        this.orderId = in.readString();
        this.bbCode = in.readString();
        this.mercId = in.readString();
        this.key = in.readString();
        this.amount = in.readString();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<SwipeParam> CREATOR = new Parcelable.Creator<SwipeParam>() {
        @Override
        public SwipeParam createFromParcel(Parcel source) {
            return new SwipeParam(source);
        }

        @Override
        public SwipeParam[] newArray(int size) {
            return new SwipeParam[size];
        }
    };
}
