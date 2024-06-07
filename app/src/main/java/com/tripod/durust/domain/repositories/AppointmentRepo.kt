package com.tripod.durust.domain.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tripod.durust.data.AppointmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AppointmentRepository(private val auth: FirebaseAuth) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun uploadAppointment(appointmentEntity: AppointmentEntity): Boolean {
        if(auth.currentUser == null) {
            Log.e("AppointmentRepository", "Error uploading appointment: uid mismatch")
            return false
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("appointments")
                    .document(appointmentEntity.id)
                    .set(appointmentEntity)
                    .await()
                true
            } catch (e: Exception) {
                Log.e("AppointmentRepository", "Error uploading appointment", e)
                false
            }
        }
    }

    suspend fun fetchAppointments(): List<AppointmentEntity>? {
        if(auth.currentUser == null) {
            Log.e("AppointmentRepository", "Error fetching appointments: uid mismatch")
            return null
        }
        return withContext(Dispatchers.IO) {
            try {
                val querySnapshot = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("appointments")
                    .get()
                    .await()

                querySnapshot.toObjects(AppointmentEntity::class.java)
            } catch (e: Exception) {
                Log.e("AppointmentRepository", "Error fetching appointments", e)
                null
            }
        }
    }
}