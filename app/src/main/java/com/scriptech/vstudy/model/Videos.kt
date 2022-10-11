package com.scriptech.vstudy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "videos"
)
@Parcelize
data class Videos(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var img: String? = null,
    var link: String? = null
): Parcelable