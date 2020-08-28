package com.yfy.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContactsGroup implements Parcelable {

    /**
     * depid : 6
     * depname : 行政部
     */

    private String depid;
    private String depname;
    private List<ChildBean> userinfob;

    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public List<ChildBean> getUserinfob() {
        return userinfob;
    }

    public void setUserinfob(List<ChildBean> userinfob) {
        this.userinfob = userinfob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.depid);
        dest.writeString(this.depname);
        dest.writeTypedList(this.userinfob);
    }

    public ContactsGroup() {
    }

    protected ContactsGroup(Parcel in) {
        this.depid = in.readString();
        this.depname = in.readString();
        this.userinfob = in.createTypedArrayList(ChildBean.CREATOR);
    }

    public static final Creator<ContactsGroup> CREATOR = new Creator<ContactsGroup>() {
        @Override
        public ContactsGroup createFromParcel(Parcel source) {
            return new ContactsGroup(source);
        }

        @Override
        public ContactsGroup[] newArray(int size) {
            return new ContactsGroup[size];
        }
    };
}
