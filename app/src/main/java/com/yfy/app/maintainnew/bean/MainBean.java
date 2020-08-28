package com.yfy.app.maintainnew.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yfyandr on 2017/12/28.
 */

public class MainBean implements Parcelable {


    /**
     * id : 1
     * user_name : 维护人员1
     * user_avatar : http://www.cdthxx.com/uploadfile/photo/20190711111940.jpg
     * submit_time : 2019/07/11 11:28
     * maintain_time :
     * department_name : 信息技术组
     * address :
     * goods :
     * content : 测试
     * image : ["http://www.cdthxx.com/uploadfile/wx/259/201907111128327720.jpg"]
     * dealstate : 申请维修
     * dealstateid : 0
     * money :
     * canout : true
     * cansetmoney : false
     * isout : 0
     * canedit : false
     * star :
     * votecontent :
     */

    private String id;
    private String user_name;
    private String user_avatar;
    private String submit_time;
    private String maintain_time;
    private String department_name;
    private String address;
    private String goods;
    private String content;
    private String dealstate;
    private String dealstateid;
    private String money;
    private String canout;
    private String cansetmoney;
    private String isout;
    private String canedit;
    private String star;
    private String votecontent;
    private String isremark;
    private List<String> image;
    private List<FlowBean> maintain_info;

    public List<FlowBean> getMaintain_info() {
        return maintain_info;
    }

    public void setMaintain_info(List<FlowBean> maintain_info) {
        this.maintain_info = maintain_info;
    }


    public String getIsremark() {
        return isremark;
    }

    public void setIsremark(String isremark) {
        this.isremark = isremark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getMaintain_time() {
        return maintain_time;
    }

    public void setMaintain_time(String maintain_time) {
        this.maintain_time = maintain_time;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDealstate() {
        return dealstate;
    }

    public void setDealstate(String dealstate) {
        this.dealstate = dealstate;
    }

    public String getDealstateid() {
        return dealstateid;
    }

    public void setDealstateid(String dealstateid) {
        this.dealstateid = dealstateid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCanout() {
        return canout;
    }

    public void setCanout(String canout) {
        this.canout = canout;
    }

    public String getCansetmoney() {
        return cansetmoney;
    }

    public void setCansetmoney(String cansetmoney) {
        this.cansetmoney = cansetmoney;
    }

    public String getIsout() {
        return isout;
    }

    public void setIsout(String isout) {
        this.isout = isout;
    }

    public String getCanedit() {
        return canedit;
    }

    public void setCanedit(String canedit) {
        this.canedit = canedit;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getVotecontent() {
        return votecontent;
    }

    public void setVotecontent(String votecontent) {
        this.votecontent = votecontent;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_name);
        dest.writeString(this.user_avatar);
        dest.writeString(this.submit_time);
        dest.writeString(this.maintain_time);
        dest.writeString(this.department_name);
        dest.writeString(this.address);
        dest.writeString(this.goods);
        dest.writeString(this.content);
        dest.writeString(this.dealstate);
        dest.writeString(this.dealstateid);
        dest.writeString(this.money);
        dest.writeString(this.canout);
        dest.writeString(this.cansetmoney);
        dest.writeString(this.isout);
        dest.writeString(this.canedit);
        dest.writeString(this.star);
        dest.writeString(this.votecontent);
        dest.writeStringList(this.image);
        dest.writeTypedList(this.maintain_info);
    }

    public MainBean() {
    }

    protected MainBean(Parcel in) {
        this.id = in.readString();
        this.user_name = in.readString();
        this.user_avatar = in.readString();
        this.submit_time = in.readString();
        this.maintain_time = in.readString();
        this.department_name = in.readString();
        this.address = in.readString();
        this.goods = in.readString();
        this.content = in.readString();
        this.dealstate = in.readString();
        this.dealstateid = in.readString();
        this.money = in.readString();
        this.canout = in.readString();
        this.cansetmoney = in.readString();
        this.isout = in.readString();
        this.canedit = in.readString();
        this.star = in.readString();
        this.votecontent = in.readString();
        this.image = in.createStringArrayList();
        this.maintain_info = in.createTypedArrayList(FlowBean.CREATOR);
    }

    public static final Creator<MainBean> CREATOR = new Creator<MainBean>() {
        @Override
        public MainBean createFromParcel(Parcel source) {
            return new MainBean(source);
        }

        @Override
        public MainBean[] newArray(int size) {
            return new MainBean[size];
        }
    };
}
