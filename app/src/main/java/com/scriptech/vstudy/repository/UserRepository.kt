package com.scriptech.vstudy.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.model.UserInfo
import kotlinx.coroutines.tasks.await

class UserRepository {

    val db = FirebaseFirestore.getInstance()

    suspend fun getUserInfo(uid: String): UserInfo? {
        return db.collection("Users").document(uid).get().await().toObject(UserInfo::class.java)
    }

}