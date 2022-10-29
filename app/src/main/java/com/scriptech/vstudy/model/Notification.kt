package com.scriptech.vstudy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "notification"
)
@Parcelize
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var notification_name: String? = null,
    var notification_img: String? = null,
    var notification_link: String? = null,
    var notification_date: String? = null
): Parcelable