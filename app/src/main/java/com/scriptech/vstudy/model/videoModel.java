package com.scriptech.vstudy.model;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class videoModel extends ArrayList<videoModel> {

    @PropertyName("video_img")
    public String video_img;
    @PropertyName("video_link")
    public String video_link;

    public String getVideoImage() {
        return video_img;
    }

    public String getVideoLink() {
        return video_link;
    }

}
