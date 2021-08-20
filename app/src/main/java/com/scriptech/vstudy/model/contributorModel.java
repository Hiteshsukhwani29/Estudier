package com.scriptech.vstudy.model;

import com.google.firebase.firestore.PropertyName;

public class contributorModel {
    @PropertyName("contributor_img")
    public String contributor_img;
    @PropertyName("contributor_link")
    public String contributor_link;

    public String getContributorImage() {
        return contributor_img;
    }

    public String getContributorLink() {
        return contributor_link;
    }
}
