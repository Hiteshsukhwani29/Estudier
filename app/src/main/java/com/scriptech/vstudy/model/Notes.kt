package com.scriptech.vstudy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "notes"
)
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var notes_name: String? = null,
    var notes_link: String? = null
): Parcelable