package com.yfy.app.tea_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChartInfo implements Parcelable {
    private String year;
    private String yearname;
    private String comments;
    private List<ChartBean> info;


    public String getYearname() {
        return yearname;
    }

    public void setYearname(String yearname) {
        this.yearname = yearname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<ChartBean> getInfo() {
        return info;
    }

    public void setInfo(List<ChartBean> info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.year);
        dest.writeString(this.yearname);
        dest.writeString(this.comments);
        dest.writeTypedList(this.info);
    }

    public ChartInfo() {
    }

    protected ChartInfo(Parcel in) {
        this.year = in.readString();
        this.yearname = in.readString();
        this.comments = in.readString();
        this.info = in.createTypedArrayList(ChartBean.CREATOR);
    }

    public static final Creator<ChartInfo> CREATOR = new Creator<ChartInfo>() {
        @Override
        public ChartInfo createFromParcel(Parcel source) {
            return new ChartInfo(source);
        }

        @Override
        public ChartInfo[] newArray(int size) {
            return new ChartInfo[size];
        }
    };
}
