package com.yfy.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ClassGrade implements Parcelable {

    private int view_type;
    private int allNum;
    private int selectNum;
    private int spanCount;
    private boolean groupChick=false;//整组选择
    private boolean expand=false;//展开|收索
    private List<Integer> child_indexs;//用于child刷新


    private String gradename;
    private String gradeid;
    private List<String> group_tag;//group标签
    /**
     * classname : 2班
     * classid : 197
     */

    private String classname;
    private String classid;
    private boolean isChick=false;


    public ClassGrade(int view_type) {
        this.view_type = view_type;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public boolean isChick() {
        return isChick;
    }

    public void setChick(boolean chick) {
        isChick = chick;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public boolean isGroupChick() {
        return groupChick;
    }

    public void setGroupChick(boolean groupChick) {
        this.groupChick = groupChick;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }



    public List<Integer> getChild_indexs() {
        return child_indexs;
    }

    public void setChild_indexs(List<Integer> child_indexs) {
        this.child_indexs = child_indexs;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public List<String> getGroup_tag() {
        return group_tag;
    }

    public void setGroup_tag(List<String> group_tag) {
        this.group_tag = group_tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.view_type);
        dest.writeInt(this.allNum);
        dest.writeInt(this.selectNum);
        dest.writeInt(this.spanCount);
        dest.writeByte(this.groupChick ? (byte) 1 : (byte) 0);
        dest.writeByte(this.expand ? (byte) 1 : (byte) 0);
        dest.writeList(this.child_indexs);
        dest.writeString(this.gradename);
        dest.writeString(this.gradeid);
        dest.writeStringList(this.group_tag);
        dest.writeString(this.classname);
        dest.writeString(this.classid);
        dest.writeByte(this.isChick ? (byte) 1 : (byte) 0);
    }

    protected ClassGrade(Parcel in) {
        this.view_type = in.readInt();
        this.allNum = in.readInt();
        this.selectNum = in.readInt();
        this.spanCount = in.readInt();
        this.groupChick = in.readByte() != 0;
        this.expand = in.readByte() != 0;
        this.child_indexs = new ArrayList<Integer>();
        in.readList(this.child_indexs, Integer.class.getClassLoader());
        this.gradename = in.readString();
        this.gradeid = in.readString();
        this.group_tag = in.createStringArrayList();
        this.classname = in.readString();
        this.classid = in.readString();
        this.isChick = in.readByte() != 0;
    }

    public static final Creator<ClassGrade> CREATOR = new Creator<ClassGrade>() {
        @Override
        public ClassGrade createFromParcel(Parcel source) {
            return new ClassGrade(source);
        }

        @Override
        public ClassGrade[] newArray(int size) {
            return new ClassGrade[size];
        }
    };
}
