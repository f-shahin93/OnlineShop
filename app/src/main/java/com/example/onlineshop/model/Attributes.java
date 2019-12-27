package com.example.onlineshop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attributes {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("position")
    private int position;

    @SerializedName("visible")
    private boolean visible;


    @SerializedName("variation")
    private boolean variation;

    @SerializedName("options")
    private List<String> options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVariation() {
        return variation;
    }

    public void setVariation(boolean variation) {
        this.variation = variation;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
