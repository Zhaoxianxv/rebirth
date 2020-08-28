package com.yfy.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DateBean implements Parcelable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public DateBean() {
    }

    protected DateBean(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<DateBean> CREATOR = new Creator<DateBean>() {
        @Override
        public DateBean createFromParcel(Parcel source) {
            return new DateBean(source);
        }

        @Override
        public DateBean[] newArray(int size) {
            return new DateBean[size];
        }
    };
}
