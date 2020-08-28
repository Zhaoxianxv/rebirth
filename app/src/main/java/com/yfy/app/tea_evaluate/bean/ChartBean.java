package com.yfy.app.tea_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ChartBean implements Parcelable {

    /**
     * title : 课例类
     * score : 0
     * middle_score : 0//中位数
     * max_score : 0
     * comments :
     * id : 2
     */

    private String title;
    private String score;
    private String middle_score;
    private String max_score;
    private String comments;
    private String id;
    private int view_type;

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMiddle_score() {
        return middle_score;
    }

    public void setMiddle_score(String middle_score) {
        this.middle_score = middle_score;
    }

    public String getMax_score() {
        return max_score;
    }

    public void setMax_score(String max_score) {
        this.max_score = max_score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        dest.writeString(this.title);
        dest.writeString(this.score);
        dest.writeString(this.middle_score);
        dest.writeString(this.max_score);
        dest.writeString(this.comments);
        dest.writeString(this.id);
        dest.writeInt(this.view_type);
    }

    public ChartBean() {
    }

    protected ChartBean(Parcel in) {
        this.title = in.readString();
        this.score = in.readString();
        this.middle_score = in.readString();
        this.max_score = in.readString();
        this.comments = in.readString();
        this.id = in.readString();
        this.view_type = in.readInt();
    }

    public static final Creator<ChartBean> CREATOR = new Creator<ChartBean>() {
        @Override
        public ChartBean createFromParcel(Parcel source) {
            return new ChartBean(source);
        }

        @Override
        public ChartBean[] newArray(int size) {
            return new ChartBean[size];
        }
    };
}
