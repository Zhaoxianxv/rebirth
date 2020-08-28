package com.yfy.app.tea_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class KeValue implements Parcelable {

    /**
     * value : 国家级（正规刊物，有刊号）
     * remark :
     */

    private String value;
    private String remark;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        dest.writeString(this.value);
        dest.writeString(this.remark);
    }

    public KeValue() {
    }

    protected KeValue(Parcel in) {
        this.value = in.readString();
        this.remark = in.readString();
    }

    public static final Creator<KeValue> CREATOR = new Creator<KeValue>() {
        @Override
        public KeValue createFromParcel(Parcel source) {
            return new KeValue(source);
        }

        @Override
        public KeValue[] newArray(int size) {
            return new KeValue[size];
        }
    };
}
