package com.yfy.app.tea_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BarItem implements Parcelable {
    private String title;
    private String score;
    private String middle_score;
    private String max_score;
    private String comments;
    private String id;
    private int view_type;
    private String year;
    private String yearname;

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

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYearname() {
        return yearname;
    }

    public void setYearname(String yearname) {
        this.yearname = yearname;
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
        dest.writeString(this.year);
        dest.writeString(this.yearname);
    }

    public BarItem() {
    }

    protected BarItem(Parcel in) {
        this.title = in.readString();
        this.score = in.readString();
        this.middle_score = in.readString();
        this.max_score = in.readString();
        this.comments = in.readString();
        this.id = in.readString();
        this.view_type = in.readInt();
        this.year = in.readString();
        this.yearname = in.readString();
    }

    public static final Creator<BarItem> CREATOR = new Creator<BarItem>() {
        @Override
        public BarItem createFromParcel(Parcel source) {
            return new BarItem(source);
        }

        @Override
        public BarItem[] newArray(int size) {
            return new BarItem[size];
        }
    };
}
