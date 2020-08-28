package com.yfy.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class UserNameId implements Parcelable {
    private String user_name;
    private String user_id;
    private boolean tick;

   
    public boolean isTick() {
        return tick;
    }

    public void setTick(boolean tick) {
        this.tick = tick;
    }

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.user_name);
		dest.writeString(this.user_id);
		dest.writeByte(this.tick ? (byte) 1 : (byte) 0);
	}

	public UserNameId() {
	}

	protected UserNameId(Parcel in) {
		this.user_name = in.readString();
		this.user_id = in.readString();
		this.tick = in.readByte() != 0;
	}

	public static final Creator<UserNameId> CREATOR = new Creator<UserNameId>() {
		@Override
		public UserNameId createFromParcel(Parcel source) {
			return new UserNameId(source);
		}

		@Override
		public UserNameId[] newArray(int size) {
			return new UserNameId[size];
		}
	};
}
