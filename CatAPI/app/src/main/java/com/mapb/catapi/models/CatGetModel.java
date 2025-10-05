package com.mapb.catapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CatGetModel {
    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    @SerializedName("breeds")
    ArrayList<Object> breeds = new ArrayList<Object>();

    @SerializedName("width")
    private float width;

    @SerializedName("height")
    private float height;

    public CatGetModel(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}