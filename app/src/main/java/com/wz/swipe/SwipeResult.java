package com.wz.swipe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangzhenguo on 2017/6/13.
 * 刷卡支付返回结果
 */

public class SwipeResult implements Parcelable {
    /**
     * 交易状态码
     */
    private String code;
    /**
     * 交易返回的描述
     */
    private String description;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单描述
     */
    private String remark;
    /**
     * json字符串
     */
    private String jsonStr;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.description);
        dest.writeString(this.orderId);
        dest.writeString(this.remark);
        dest.writeString(this.jsonStr);
    }

    public void readFromParcel(Parcel source){
        code = source.readString();
        description = source.readString();
        orderId = source.readString();
        remark = source.readString();
        jsonStr = source.readString();
    }

    public SwipeResult() {
    }

    protected SwipeResult(Parcel in) {
        this.code = in.readString();
        this.description = in.readString();
        this.orderId = in.readString();
        this.remark = in.readString();
        this.jsonStr = in.readString();
    }

    public static final Parcelable.Creator<SwipeResult> CREATOR = new Parcelable.Creator<SwipeResult>() {
        @Override
        public SwipeResult createFromParcel(Parcel source) {
            return new SwipeResult(source);
        }

        @Override
        public SwipeResult[] newArray(int size) {
            return new SwipeResult[size];
        }
    };
}
