package com.tripod.durust.domain.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tripod.durust.data.PrimaryUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PrimaryUserDataRepo(val auth: FirebaseAuth) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getUserData(): PrimaryUserData? {
        if(auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return null
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .get()
                    .await()?.toObject(PrimaryUserData::class.java)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error getting user data", e)
                null
            }
        }
    }

    suspend fun setUserData(user: PrimaryUserData): Boolean {
        if(auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return false
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .set(user)
                    .await()
                true
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                false
            }
        }
    }
}