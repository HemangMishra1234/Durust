package com.tripod.durust.domain.repositories


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tripod.durust.presentation.chats.data.BotComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GeminiChatRepository(private val auth: FirebaseAuth) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun uploadChatData(chatData: BotComponent): Boolean {
        if(auth.currentUser == null) {
            Log.e("GeminiChatRepository", "Error uploading chat data: uid mismatch")
            return false
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("chats")
                    .document("chatData")
                    .set(chatData)
                    .await()
                true
            } catch (e: Exception) {
                Log.e("GeminiChatRepository", "Error uploading chat data", e)
                false
            }
        }
    }

    suspend fun fetchChatData(): BotComponent? {
        if(auth.currentUser == null) {
            Log.e("GeminiChatRepository", "Error fetching chat data: uid mismatch")
            return null
        }
        return withContext(Dispatchers.IO) {
            try {
                val document = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("chats")
                    .document("chatData")
                    .get()
                    .await()

                document.toObject(BotComponent::class.java)
            } catch (e: Exception) {
                Log.e("GeminiChatRepository", "Error fetching chat data", e)
                null
            }
        }
    }
}