package com.tripod.durust.data

import com.tripod.durust.presentation.chats.individual.MedicineFrequency
import com.tripod.durust.presentation.chats.individual.MedicineTime
import com.tripod.durust.presentation.datacollection.ExerciseType
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.WakeSleepEntity
import com.tripod.durust.presentation.datacollection.getTime
import kotlinx.coroutines.flow.Flow

data class ExerciseTrackEntity(
    val date: Long = getTime(),
    val exerciseType: ExerciseType,
    val duration: TimeEntity,
    val caloriesBurnt: Int
)

data class SleepTrackEntity(
    val date: Long = getTime(),
    val duration: WakeSleepEntity
)

data class MedicineTrackEntity(
    val date: Long = getTime(),
    val medicineName: String,
    val time: MedicineTime,
    val isMedicineDaily: Boolean,
    val medicineFrequency: MedicineFrequency
)

data class WaterTrackEntity(
    val date: Long = getTime(),
    val waterQuantity: Int
)

data class FoodTrackEntity(
    val date: Long = getTime(),
    val foodName: String,
    val calories: Int
)

data class WeightTrackEntity(
    val date: Long = getTime(),
    val weight: Float
)

data class BloodPressureTrackEntity(
    val date: Long = getTime(),
    val systolic: Int,
    val diastolic: Int
)

data class GlucoseTrackEntity(
    val date: Long = getTime(),
    val glucoseLevel: Int
)

data class AllTrackEntities(
    val exerciseTrackEntity: List<ExerciseTrackEntity>? = null,
    val sleepTrackEntity: SleepTrackEntity? = null,
    val medicineTrackEntity: MedicineTrackEntity? = null,
    val waterTrackEntity: WaterTrackEntity? = null,
    val foodTrackEntity: FoodTrackEntity? = null,
    val weightTrackEntity: WeightTrackEntity? = null,
    val bloodPressureTrackEntity: BloodPressureTrackEntity? = null,
    val glucoseTrackEntity: GlucoseTrackEntity? = null,
    val isWaterTrackLoaded: WaterTrackEntity? = null,
    var isPrimaryUserData: PrimaryUserData? = null
)