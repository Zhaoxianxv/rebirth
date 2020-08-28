package com.yfy.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NameId implements Parcelable {


    /**
     * id : 518
     * name : 何运强
     */

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public NameId() {
    }

    protected NameId(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<NameId> CREATOR = new Creator<NameId>() {
        @Override
        public NameId createFromParcel(Parcel source) {
            return new NameId(source);
        }

        @Override
        public NameId[] newArray(int size) {
            return new NameId[size];
        }
    };
}
