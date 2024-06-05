package com.tripod.durust.data

import com.tripod.durust.presentation.datacollection.CheckUpFrequency
import com.tripod.durust.presentation.datacollection.ExerciseType
import com.tripod.durust.presentation.datacollection.GenderEntity

data class PrimaryUserData(
    val name: String = "",
    val gender: GenderEntity = GenderEntity.Female,
    val weight: Int = 70,
    val birthday: DateEntity = DateEntity(5,5,2005),
    val height:String = "5'5",
    val exerciseFrequency: Int = 3,
    val exercisePreference: ExerciseType = ExerciseType.WALKING,
    val checkUpFrequency: CheckUpFrequency = CheckUpFrequency.NEVER,
)
