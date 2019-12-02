package com.example.onlineshop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem implements Parcelable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;

	protected CategoriesItem(Parcel in) {
		name = in.readString();
		id = in.readInt();
		slug = in.readString();
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(name);
		parcel.writeInt(id);
		parcel.writeString(slug);
	}

	public static final Creator<CategoriesItem> CREATOR = new Creator<CategoriesItem>() {
		@Override
		public CategoriesItem createFromParcel(Parcel in) {
			return new CategoriesItem(in);
		}

		@Override
		public CategoriesItem[] newArray(int size) {
			return new CategoriesItem[size];
		}
	};
}