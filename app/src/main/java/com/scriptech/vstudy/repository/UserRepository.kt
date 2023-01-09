package com.scriptech.vstudy.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.model.FeedbackModel
import com.scriptech.vstudy.model.Notification
import com.scriptech.vstudy.model.UserInfo
import kotlinx.coroutines.tasks.await

class UserRepository {

    val db = FirebaseFirestore.getInstance()

    var _notificationsList: MutableList<Notification> = mutableListOf()

    fun getUserInfo(uid: String): UserInfo? {
        var userInfo: UserInfo? = null
        db.collection("Users").document(uid).get().addOnSuccessListener {
            userInfo = it.toObject(UserInfo::class.java)!!
        }
        return userInfo
    }

    suspend fun getAllNotifications(): MutableList<Notification> {
        val result = db.collection("notifications")
            .get()
            .await()
        for (document in result) {
            document.toObject(Notification::class.java).let {
                _notificationsList.add(it)
            }
        }
        return _notificationsList
    }

    suspend fun contributeToEstudier() {
        db.collection("Reports").add(FeedbackModel::class.java).await()
    }

}