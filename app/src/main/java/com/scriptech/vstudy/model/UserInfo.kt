package com.scriptech.vstudy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "userinfo"
)
@Parcelize
data class UserInfo(
    var name: String? = null,
    var number: String? = null,
    var email: String? = null
): Parcelable
