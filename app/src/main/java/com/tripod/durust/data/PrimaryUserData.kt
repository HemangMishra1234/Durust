package com.tripod.durust.data

import com.tripod.durust.presentation.datacollection.CheckUpFrequency
import com.tripod.durust.presentation.datacollection.ExerciseType
import com.tripod.durust.presentation.datacollection.GenderEntity
import com.tripod.durust.presentation.datacollection.HealthCondition
import com.tripod.durust.presentation.datacollection.MealPreferenceEntity
import com.tripod.durust.presentation.datacollection.StepsInputEntity
import com.tripod.durust.presentation.datacollection.TimeEntity

data class PrimaryUserData(
    val name: String = "",
    val gender: GenderEntity = GenderEntity.Female,
    val weight: Int = 70,
    val birthday: DateEntity = DateEntity(5,5,2005),
    val height:String = "5'5",
    val exerciseFrequency: Int = 3,
    val exercisePreference: ExerciseType = ExerciseType.WALKING,
    val stepsInput: StepsInputEntity = StepsInputEntity.GREATER_THAN_TEN_THOUSAND,
    val mealPreferenceEntity: MealPreferenceEntity = MealPreferenceEntity.VEGETARIAN,
    val wakeUpTime: TimeEntity = TimeEntity(6,0),
    val sleepTime: TimeEntity = TimeEntity(10,0),
    val calorieIntake: Int = 2000,
    val healthIssues: HealthCondition = HealthCondition.NONE,
    val checkUpFrequency: CheckUpFrequency = CheckUpFrequency.NEVER,
)
