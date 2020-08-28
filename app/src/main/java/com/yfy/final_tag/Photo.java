package com.yfy.final_tag;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {
	/**
	 *
	 */

	private String id;
	private int drawable;
	private String path;
	private String fileName="";
	private String albumName;

	private long size;
	private boolean isSelected;
	private String description = "";
	private boolean temporary;
	public Photo() {
		super();
	}
	public Photo(
			String id,
			String path,
			String albumName,
			long size,
			boolean isSelected,
			String description,
			boolean temporary) {
		super();
		this.id = id;
		this.path = path;
		this.albumName = albumName;
		this.size = size;
		this.isSelected = isSelected;
		this.description = description;
		this.temporary = temporary;
	}
	public Photo(String id, String path, String albumName, long size, boolean isSelected) {
		super();
		this.id = id;
		this.path = path;
		this.albumName = albumName;
		this.size = size;
		this.isSelected = isSelected;

	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getDrawable() {
		return drawable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", path=" + path + ", albumName="
				+ albumName + ", size=" + size + ", isSelected=" + isSelected
				+ ", description=" + description + ", temporary=" + temporary
				+ "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeInt(this.drawable);
		dest.writeString(this.path);
		dest.writeString(this.albumName);
		dest.writeLong(this.size);
		dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
		dest.writeString(this.description);
		dest.writeByte(this.temporary ? (byte) 1 : (byte) 0);
	}

	protected Photo(Parcel in) {
		this.id = in.readString();
		this.drawable = in.readInt();
		this.path = in.readString();
		this.albumName = in.readString();
		this.size = in.readLong();
		this.isSelected = in.readByte() != 0;
		this.description = in.readString();
		this.temporary = in.readByte() != 0;
	}

	public static final Creator<Photo> CREATOR = new Creator<Photo>() {
		@Override
		public Photo createFromParcel(Parcel source) {
			return new Photo(source);
		}

		@Override
		public Photo[] newArray(int size) {
			return new Photo[size];
		}
	};
}
