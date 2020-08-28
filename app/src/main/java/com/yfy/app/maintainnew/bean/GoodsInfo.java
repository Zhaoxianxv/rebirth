package com.yfy.app.maintainnew.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GoodsInfo implements Parcelable {
    /**
     * officename : 一年级一班教室
     * id : 1
     */

    private String officename;
    private String id;
    private List<GoodBean> goods;

    public List<GoodBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodBean> goods) {
        this.goods = goods;
    }

    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.officename);
        dest.writeString(this.id);
        dest.writeTypedList(this.goods);
    }

    public GoodsInfo() {
    }

    protected GoodsInfo(Parcel in) {
        this.officename = in.readString();
        this.id = in.readString();
        this.goods = in.createTypedArrayList(GoodBean.CREATOR);
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        @Override
        public GoodsInfo createFromParcel(Parcel source) {
            return new GoodsInfo(source);
        }

        @Override
        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };
}
