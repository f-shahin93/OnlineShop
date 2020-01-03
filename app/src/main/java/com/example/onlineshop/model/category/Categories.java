package com.example.onlineshop.model.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Links;
import com.google.gson.annotations.SerializedName;

public class Categories implements Parcelable {

    @SerializedName("parent")
    private int parent;

    @SerializedName("image")
    private com.example.onlineshop.model.Image image;

    @SerializedName("menu_order")
    private int menuOrder;

    @SerializedName("_links")
    private com.example.onlineshop.model.Links links;

    @SerializedName("display")
    private String display;

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int count;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    protected Categories(Parcel in) {
        parent = in.readInt();
        menuOrder = in.readInt();
        display = in.readString();
        name = in.readString();
        count = in.readInt();
        description = in.readString();
        id = in.readInt();
        slug = in.readString();
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }

    public void setImage(com.example.onlineshop.model.Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setLinks(com.example.onlineshop.model.Links links) {
        this.links = links;
    }

    public Links getLinks() {
        return links;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "parent = '" + parent + '\'' +
                        ",image = '" + image + '\'' +
                        ",menu_order = '" + menuOrder + '\'' +
                        ",_links = '" + links + '\'' +
                        ",display = '" + display + '\'' +
                        ",name = '" + name + '\'' +
                        ",count = '" + count + '\'' +
                        ",description = '" + description + '\'' +
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
        parcel.writeInt(parent);
        parcel.writeInt(menuOrder);
        parcel.writeString(display);
        parcel.writeString(name);
        parcel.writeInt(count);
        parcel.writeString(description);
        parcel.writeInt(id);
        parcel.writeString(slug);
    }
}