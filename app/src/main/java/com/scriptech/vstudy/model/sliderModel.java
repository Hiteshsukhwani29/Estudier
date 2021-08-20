package com.scriptech.vstudy.model;

import java.util.ArrayList;

public class sliderModel extends ArrayList<sliderModel> {
    private String image;

    /* @PropertyName("fea_img")
     public String fea_img;*/
    public sliderModel(String image) {
        this.image = image;
    }

    /*public sliderModel(String image){
        this.image = image;
    }
*/
    public String getImage() {
        return image;
    }
}
