package com.scriptech.vstudy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "books"
)
@Parcelize
data class Books(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var book_name: String? = null,
    var book_img: String? = null,
    var book_link: String? = null,
    var book_author: String? = null
): Parcelable
