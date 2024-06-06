package com.tripod.durust.presentation.chats.data

import com.tripod.durust.presentation.chats.individual.DataEntryCarouselEntity
import com.tripod.durust.presentation.chats.individual.GlucoseTestType
import com.tripod.durust.presentation.chats.individual.MedicineFrequency
import com.tripod.durust.presentation.chats.individual.MedicineTime
import com.tripod.durust.presentation.chats.individual.MenuOptions
import com.tripod.durust.presentation.chats.individual.StatsEntity
import com.tripod.durust.presentation.datacollection.ExerciseType
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.WakeSleepEntity

sealed class BotComponent {
    data class MenuState(
        val id: String,
        val selectedOption: MenuOptions
    ) : BotComponent()

    data class UserResponseState(
        val id: String,
        val response: String
    ) : BotComponent()

    data class GeminiResponseState(
        val id: String,
        val response: String
    ) : BotComponent()

    data class GeminiState(
        val id: String,
        val prompt: String
    ) : BotComponent()

    data class DECarouselState(
        val id: String,
        val selectedOption: DataEntryCarouselEntity
    ) : BotComponent()

    data class DEExerciseTypeCarouselState(
        val id: String,
        val selectedOption: ExerciseType
    ) : BotComponent()

    data class DEExerciseDurationState(
        val id: String,
        val duration: TimeEntity
    ) : BotComponent()

    data class DEExerciseAddOneMoreState(
        val id: String,
        val shouldAdd: Boolean
    ) : BotComponent()

    data class DESleepDurationState(
        val id: String,
        val duration: WakeSleepEntity
    ) : BotComponent()

    data class DEMedicineSearchState(
        val id: String,
        val medicine: String,
    ) : BotComponent()

    data class DEMedicineTimeState(
        val id: String,
        val time: MedicineTime
    ) : BotComponent()

    data class DEMedicineFrequencyIsDaily(
        val id: String,
        val isDaily: Boolean
    ) : BotComponent()

    data class DEMedicineWeekdaysState(
        val id: String,
        val frequency: List<MedicineFrequency>
    ) : BotComponent()

    data class DEMedicineNotificationState(
        val id: String,
        val shouldNotify: Boolean
    ) : BotComponent()

    data class DEMedicineNotificationTimeState(
        val id: String,
        val time: TimeEntity
    ) : BotComponent()

    data class DEMedicineAddOneMoreState(
        val id: String,
        val shouldAdd: Boolean
    ) : BotComponent()

    data class DEStatsCarouselState(
        val id: String,
        val selectedOption: StatsEntity

    ) : BotComponent()

    data class DEStatsWeightEntryState(
        val id: String,
        val weight: Float
    ) : BotComponent()

    data class DEStatsBloodPressureSystolicEntryState(
        val id: String,
        val systolic: Int
    ) : BotComponent()

    data class DEStatsBloodPressureDiastolicEntryState(
        val id: String,
        val diastolic: Int
    ) : BotComponent()

    data class DEStatsGlucoseTestTypeState(
        val id: String,
        val glucoseTestType: GlucoseTestType
    ) : BotComponent()


    data class DEStatsGlucoseEntryState(
        val id: String,
        val glucose: Int
    ) : BotComponent()

}

data class GeminiData(
    val selectedCarouselEntity: DataEntryCarouselEntity? = null,
    val selectedExerciseType: ExerciseType? = null,
    val exerciseDuration: TimeEntity? = null,
    val shouldAddExercise: Boolean? = null,
    val caloriesBurnt: Int? = null,
    val sleepDuration: WakeSleepEntity? = null,
    val medicineName: String? = null,
    val medicineTime: MedicineTime? = null,
    val isMedicineDaily: Boolean? = null,
    val medicineFrequency: List<MedicineFrequency>? = null,
    val shouldNotifyForMedicine: Boolean? = null,
    val medicineNotifcationTime: TimeEntity? = null,
    val shouldAddMedicine: Boolean? = null,
    val selectedStatsEntity: StatsEntity? = null,
    val weight: Float? = null,
    val systolicPressure: Int? = null,
    val diastolicPressure: Int? = null,
    val glucoseTestType: GlucoseTestType? = null,
    val glucoseLevel: Int? = null
)


enum class BotUiState(val stateSerialNo: Int){
    MENU(1),
    GEMINI(2),
    DE_CAROUSEL_STATE(3),
    DE_EXERCISE_TYPE_CAROUSEL_STATE(4),
    DE_EXERCISE_DURATION_STATE(5),
    DE_EXERCISE_ADD_ONE_MORE_STATE(6),
    DE_SLEEP_DURATION_STATE(7),
    DE_MEDICINE_SEARCH_STATE(8),
    DE_MEDICINE_TIME(9),
    DE_MEDICINE_FREQUENCY_IS_DAILY(10),
    DE_MEDICINE_WEEKDAYS_STATE(11),
    DE_MEDICINE_NOTIFICATION_STATE(12),
    DE_MEDICINE_NOTIFICATION_TIME_STATE(13),
    DE_MEDICINE_ADD_ONE_MORE_STATE(14),
    DE_STATS_CAROUSEL_STATE(15),
    DE_STATS_WEIGHT_ENTRY_STATE(16),
    DE_STATS_BLOOD_PRESSURE_SYSTOLIC_ENTRY_STATE(17),
    DE_STATS_BLOOD_PRESSURE_DIASTOLIC_ENTRY_STATE(18),
    DE_STATS_GLUCOSE_TEST_TYPE_STATE(19),
    DE_STATS_GLUCOSE_ENTRY_STATE(20)
}