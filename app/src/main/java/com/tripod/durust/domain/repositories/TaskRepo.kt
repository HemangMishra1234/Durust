package com.tripod.durust.domain.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tripod.durust.presentation.home.individuals.TaskEntity
import com.tripod.durust.presentation.home.individuals.TaskListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate

class TaskRepository(private val auth: FirebaseAuth) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun uploadTasks(taskListEntity: TaskListEntity): Boolean {
        if(auth.currentUser == null) {
            Log.e("TaskRepository", "Error uploading tasks: uid mismatch")
            return false
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("tasks")
                    .document("taskList")
                    .set(taskListEntity)
                    .await()
                true
            } catch (e: Exception) {
                Log.e("TaskRepository", "Error uploading tasks", e)
                false
            }
        }
    }

    suspend fun fetchTasks(): TaskListEntity? {
        if(auth.currentUser == null) {
            Log.e("TaskRepository", "Error fetching tasks: uid mismatch")
            return null
        }
        return withContext(Dispatchers.IO) {
            try {
                val document = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("tasks")
                    .document("taskList")
                    .get()
                    .await()

                document.toObject(TaskListEntity::class.java)
            } catch (e: Exception) {
                Log.e("TaskRepository", "Error fetching tasks", e)
                null
            }
        }
    }
}