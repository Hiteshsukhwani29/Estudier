package com.scriptech.vstudy.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Notification
import com.scriptech.vstudy.model.UserInfo
import kotlinx.coroutines.tasks.await

class UserRepository {

    val db = FirebaseFirestore.getInstance()

    var _notificationsList: MutableList<Notification> = mutableListOf()

    suspend fun getUserInfo(uid: String): UserInfo? {
        return db.collection("Users").document(uid).get().await().toObject(UserInfo::class.java)
    }

    suspend fun getAllNotifications(): MutableList<Notification> {
        val result = db.collection("notifications")
            .get()
            .await()
        for (document in result) {
            document.toObject(Notification::class.java).let{
                _notificationsList.add(it)
            }
        }
        return _notificationsList
    }

}