package com.mapb.catapi.models;

import com.google.gson.annotations.SerializedName;

public class FavoriteCatPostModel {
    @SerializedName("id")
    private float id;

    @SerializedName("image_id")
    private String image_id;

    @SerializedName("sub_id")
    private String sub_id;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("image")
    Image ImageObject;

    public FavoriteCatPostModel(String imageId, String sub_id) {
        this.image_id = imageId;
        this.sub_id = sub_id;
    }

    public float getId() {
        return id;
    }

    public String getImage_id() {
        return image_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Image getImage() {
        return ImageObject;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setImage(Image imageObject) {
        this.ImageObject = imageObject;
    }

    public class Image {
        @SerializedName("id")
        private String id;

        @SerializedName("url")
        private String url;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}

