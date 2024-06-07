package com.tripod.durust.domain.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tripod.durust.data.BloodPressureTrackEntity
import com.tripod.durust.data.ExerciseTrackEntity
import com.tripod.durust.data.FoodTrackEntity
import com.tripod.durust.data.GlucoseTrackEntity
import com.tripod.durust.data.MedicineTrackEntity
import com.tripod.durust.data.SleepTrackEntity
import com.tripod.durust.data.WaterTrackEntity
import com.tripod.durust.data.WeightTrackEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TrackRepository(val auth: FirebaseAuth) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun setExerciseTrack(trackEntity: ExerciseTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("exerciseTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllExerciseTracks(): Flow<List<ExerciseTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("exerciseTracks")
                    .get()
                    .await()
                    .map { it.toObject(ExerciseTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

    suspend fun setSleepTrack(trackEntity: SleepTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("sleepTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllSleepTracks(): Flow<List<SleepTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("sleepTracks")
                    .get()
                    .await()
                    .map { it.toObject(SleepTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

    suspend fun setMedicineTrack(trackEntity: MedicineTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("medicineTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllMedicineTracks(): Flow<List<MedicineTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("medicineTracks")
                    .get()
                    .await()
                    .map { it.toObject(MedicineTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

    suspend fun setWaterTrack(trackEntity: WaterTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("waterTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllWaterTracks(): Flow<List<WaterTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("waterTracks")
                    .get()
                    .await()
                    .map { it.toObject(WaterTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

    suspend fun setFoodTrack(trackEntity: FoodTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("foodTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllFoodTracks(): Flow<List<FoodTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("foodTracks")
                    .get()
                    .await()
                    .map { it.toObject(FoodTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

    suspend fun setWeightTrack(trackEntity: WeightTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("weightTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllWeightTracks(): Flow<List<WeightTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("weightTracks")
                    .get()
                    .await()
                    .map { it.toObject(WeightTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

    suspend fun setBloodPressureTrack(trackEntity: BloodPressureTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("bloodPressureTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllBloodPressureTracks(): Flow<List<BloodPressureTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("bloodPressureTracks")
                    .get()
                    .await()
                    .map { it.toObject(BloodPressureTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

    suspend fun setGlucoseTrack(trackEntity: GlucoseTrackEntity) {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            return
        }
        return withContext(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("glucoseTracks")
                    .document(trackEntity.date.toString())
                    .set(trackEntity)
                    .await()
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
            }
        }
    }

    suspend fun getAllGlucoseTracks(): Flow<List<GlucoseTrackEntity>> = flow {
        if (auth.currentUser == null) {
            Log.e("UserDataRepository", "Error setting user data: uid mismatch")
            emit(emptyList())
        } else {
            try {
                val result = db.collection("users")
                    .document(auth.currentUser!!.uid)
                    .collection("glucoseTracks")
                    .get()
                    .await()
                    .map { it.toObject(GlucoseTrackEntity::class.java) }
                emit(result)
            } catch (e: Exception) {
                Log.e("UserDataRepository", "Error setting user data", e)
                emit(emptyList())
            }
        }
    }

}