package com.scriptech.vstudy.model;

import com.google.firebase.firestore.PropertyName;

public class notificationModel {
    @PropertyName("notification_name")
    public String notification_name;
    @PropertyName("notification_img")
    public String notification_img;
    @PropertyName("notification_link")
    public String notification_link;
    @PropertyName("notification_date")
    public String notification_date;

    public String getNotificationName() {
        return notification_name;
    }

    public String getNotificationImage() {
        return notification_img;
    }

    public String getNotificationLink() {
        return notification_link;
    }

    public String getNotificationDate() {
        return notification_date;
    }

}
