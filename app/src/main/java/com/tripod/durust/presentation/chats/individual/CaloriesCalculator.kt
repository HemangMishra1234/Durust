package com.tripod.durust.presentation.chats.individual

import com.tripod.durust.presentation.datacollection.ExerciseType
import kotlin.math.roundToInt

fun calculateCaloriesBurnt(exerciseType: ExerciseType, duration: TimeDuration, height: String, weight: Int): Int {
    val totalMinutes = duration.hours * 60 + duration.minutes
    val metValue = exerciseType.metValue
    val heightInInches = heightToInches(height)
    val weightInKg = weight

    // Calculate MET hours
    val metHours = metValue * (totalMinutes.toDouble() / 60)

    // Calculate calories burnt using MET formula
    val caloriesBurnt = (metHours * weightInKg).roundToInt()

    return caloriesBurnt
}

fun heightToInches(height: String): Int {
    val parts = height.split("'")
    val feet = parts[0].toInt()
    val inches = parts[1].toInt()
    return feet * 12 + inches
}