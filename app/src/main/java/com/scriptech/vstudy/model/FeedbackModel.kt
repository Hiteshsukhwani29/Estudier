package com.scriptech.vstudy.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    tableName = "feedbackInfo"
)
@Parcelize
data class FeedbackModel(
    var Date: Date? = null,
    var Name: String? = null,
    var Number: String? = null,
    var Email: String? = null,
    var Department: String? = null,
    var NotesName: String? = null,
    var NotesAuthor: String? = null,
    var BookName: String? = null,
    var BookAuthor: String? = null,
    var VideoName: String? = null,
    var VideoAuthor: String? = null,
    var ReportDescription: String? = null,
    var FeedbackDescription: String? = null,
    var Document: String? = null,
) : Parcelable
