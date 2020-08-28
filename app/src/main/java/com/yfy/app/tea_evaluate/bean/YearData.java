package com.yfy.app.tea_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class YearData implements Parcelable {


    /**
     * year : 2018
     * isnow : 是
     */

    private String year;
    private String yearname;
    private String isnow;


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

    public String getIsnow() {
        return isnow;
    }

    public void setIsnow(String isnow) {
        this.isnow = isnow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.year);
        dest.writeString(this.yearname);
        dest.writeString(this.isnow);
    }

    public YearData() {
    }

    protected YearData(Parcel in) {
        this.year = in.readString();
        this.yearname = in.readString();
        this.isnow = in.readString();
    }

    public static final Creator<YearData> CREATOR = new Creator<YearData>() {
        @Override
        public YearData createFromParcel(Parcel source) {
            return new YearData(source);
        }

        @Override
        public YearData[] newArray(int size) {
            return new YearData[size];
        }
    };
}
