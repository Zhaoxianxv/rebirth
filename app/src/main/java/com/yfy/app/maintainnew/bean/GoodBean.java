package com.yfy.app.maintainnew.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.yfy.final_tag.TagFinal;

public class GoodBean implements Parcelable {

    private int view_type=TagFinal.TYPE_ITEM;
    private String allName;

    private String officename;
    private String id;

    /**
     * goodsname : 课桌
     * goodsid : 1
     * type : 后勤
     */

    private String goodsname;
    private String goodsid;
    private String Logisticsclassid;
    private String Logisticsclassname;
    private String type;
    private boolean isSelect=false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getAllName() {
        return allName;
    }

    public void setAllName(String allName) {
        this.allName = allName;
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

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogisticsclassid() {
        return Logisticsclassid;
    }

    public void setLogisticsclassid(String logisticsclassid) {
        Logisticsclassid = logisticsclassid;
    }

    public String getLogisticsclassname() {
        return Logisticsclassname;
    }

    public void setLogisticsclassname(String logisticsclassname) {
        Logisticsclassname = logisticsclassname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.view_type);
        dest.writeString(this.allName);
        dest.writeString(this.officename);
        dest.writeString(this.id);
        dest.writeString(this.goodsname);
        dest.writeString(this.goodsid);
        dest.writeString(this.Logisticsclassid);
        dest.writeString(this.Logisticsclassname);
        dest.writeString(this.type);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public GoodBean() {
    }

    protected GoodBean(Parcel in) {
        this.view_type = in.readInt();
        this.allName = in.readString();
        this.officename = in.readString();
        this.id = in.readString();
        this.goodsname = in.readString();
        this.goodsid = in.readString();
        this.Logisticsclassid = in.readString();
        this.Logisticsclassname = in.readString();
        this.type = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<GoodBean> CREATOR = new Creator<GoodBean>() {
        @Override
        public GoodBean createFromParcel(Parcel source) {
            return new GoodBean(source);
        }

        @Override
        public GoodBean[] newArray(int size) {
            return new GoodBean[size];
        }
    };
}
