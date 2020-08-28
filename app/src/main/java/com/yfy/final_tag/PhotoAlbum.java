package com.yfy.final_tag;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PhotoAlbum implements Parcelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5785626561726745376L;
	private String id;
	private String name;
	public ArrayList<Photo> photoList;

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
	public String toString() {
		return "PhotoAblum [id=" + id + ", name=" + name + ", photoList="
				+ photoList + "]";
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeTypedList(this.photoList);
	}

	public PhotoAlbum() {
	}

	protected PhotoAlbum(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.photoList = in.createTypedArrayList(Photo.CREATOR);
	}

	public static final Creator<PhotoAlbum> CREATOR = new Creator<PhotoAlbum>() {
		@Override
		public PhotoAlbum createFromParcel(Parcel source) {
			return new PhotoAlbum(source);
		}

		@Override
		public PhotoAlbum[] newArray(int size) {
			return new PhotoAlbum[size];
		}
	};
}
